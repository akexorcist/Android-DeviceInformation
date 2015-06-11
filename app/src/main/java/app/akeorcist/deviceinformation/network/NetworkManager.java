package app.akeorcist.deviceinformation.network;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;

import app.akeorcist.deviceinformation.constants.ErrorMessage;
import app.akeorcist.deviceinformation.constants.URL;
import app.akeorcist.deviceinformation.data.device.hardware.ExternalStorageManager;
import app.akeorcist.deviceinformation.data.device.hardware.StorageManager;
import app.akeorcist.deviceinformation.data.file.FileManager;
import app.akeorcist.deviceinformation.event.DeviceNameResultEvent;
import app.akeorcist.deviceinformation.event.DeviceQueryEvent;
import app.akeorcist.deviceinformation.event.DeviceSwitchEvent;
import app.akeorcist.deviceinformation.event.SubmitEvent;
import app.akeorcist.deviceinformation.model.DeviceList;
import app.akeorcist.deviceinformation.model.SimpleData;
import app.akeorcist.deviceinformation.model.SubDevice;
import app.akeorcist.deviceinformation.provider.BusProvider;
import app.akeorcist.deviceinformation.utility.StringUtils;

/**
 * Created by Ake on 3/2/2015.
 */
public class NetworkManager {
    private Call deviceInfoCall = null;
    private OkHttpClient client = new OkHttpClient();

    public void sendDeviceInfo(String brand, String model, String version, String fingerprint, String data) {
        String url = URL.URL_ADD_DEVICE;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("brand", brand);
            jsonObject.put("model", model);
            jsonObject.put("version", version);
            jsonObject.put("fingerprint", fingerprint);
            jsonObject.put("device_info", data);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    SubmitEvent event = new SubmitEvent(SubmitEvent.EVENT_SEND_FAILED);
                    event.setMessage("Connection failure");
                    BusProvider.getNetworkInstance().post(event);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    BusProvider.getNetworkInstance().post(new SubmitEvent(SubmitEvent.EVENT_SEND_FINISHED));
                }
            });
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    public void sendTestOnParse(String data) {
        ParseObject parseObject = new ParseObject("TestData");
        parseObject.put("JSON", data);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.e("Check", "Done");
            }
        });
    }*/


    // TODO Collect Storage Path
    public static void sendSD(final Context context) {

        String str = "";
        HashSet<String> externalLocations = ExternalStorageManager.getStorageSet();
        for(int i = 0 ; i < externalLocations.size() ; i++) {
            Object[] myArr = externalLocations.toArray();
            String path = myArr[0].toString().toLowerCase(Locale.getDefault());
            str += path + "\n";
        }

        ParseObject parseObject = new ParseObject("SD");
        parseObject.put("brand", Build.BRAND);
        parseObject.put("model", Build.MODEL);
        parseObject.put("sd", str);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(context, "Finish", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkDeviceExist(String brand, String model, String version, String fingerprint) {
        String url = URL.URL_CHECK_EXIST;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("brand", brand);
            jsonObject.put("model", model);
            jsonObject.put("version", version);
            jsonObject.put("fingerprint", fingerprint);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    SubmitEvent event = new SubmitEvent(SubmitEvent.EVENT_CONNECTION_UNAVAILABLE);
                    event.setMessage("Connection unavailable");
                    BusProvider.getNetworkInstance().post(event);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean isExist = jsonObject.getBoolean("exist");
                        if (isExist)
                            BusProvider.getNetworkInstance().post(new SubmitEvent(SubmitEvent.EVENT_DEVICE_EXIST));
                        else
                            BusProvider.getNetworkInstance().post(new SubmitEvent(SubmitEvent.EVENT_DEVICE_NOT_EXIST));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void getDeviceName(String fingerprint) {
        String url = URL.URL_GET_DEVICE_NAME;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fingerprint", fingerprint);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    // TODO Handler when failed to get device name
                    DeviceNameResultEvent event = new DeviceNameResultEvent(DeviceNameResultEvent.RESULT_FAILURE);
                    BusProvider.getNetworkInstance().post(event);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String message = jsonObject.getString("message");
                        if ("found".equals(message)) {
                            String name = jsonObject.getString("device_name");
                            String brand = jsonObject.getString("device_brand");
                            String model = jsonObject.getString("device_model");
                            String image = jsonObject.getString("device_image");
                            if (!name.equals("") && !brand.equals("")) {
                                DeviceNameResultEvent event = new DeviceNameResultEvent(DeviceNameResultEvent.RESULT_FOUND);
                                event.setDeviceName(name);
                                event.setDeviceBrand(brand);
                                event.setDeviceModel(model);
                                event.setDeviceImage(image);
                                BusProvider.getNetworkInstance().post(event);
                            }
                        } else if("not_found".equals(message)) {
                            DeviceNameResultEvent event = new DeviceNameResultEvent(DeviceNameResultEvent.RESULT_NOT_FOUND);
                            BusProvider.getNetworkInstance().post(event);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAllBrand() {
        String url = URL.URL_GET_ALL_BRAND;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // TODO Handler then can't get all brand list
                getAllBrandFailed(ErrorMessage.CONNECTION_FAILURE);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String message = jsonObject.getString("message");
                    if("success".equals(message)) {
                        ArrayList<String> brandList = new ArrayList<String>();
                        JSONArray jsonArray = jsonObject.getJSONArray("brand_list");
                        if(jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                brandList.add(jsonArray.getString(i));
                            }
                        }
                        Collections.sort(brandList, new Comparator<String>() {
                            public int compare(String s1, String s2) {
                                return s1.compareToIgnoreCase(s2);
                            }
                        });

                        DeviceQueryEvent event = new DeviceQueryEvent(DeviceQueryEvent.RESULT_SUCCESS);
                        event.setBrandType();
                        event.setBrandList(brandList);
                        BusProvider.getNetworkInstance().post(event);
                    } else {
                        getAllBrandFailed(ErrorMessage.SERVER_PROBLEM);
                    }
                } catch (JSONException e) {
                    getAllBrandFailed(ErrorMessage.RESPONSE_FAILURE);
                }
            }
        });
    }

    private void getAllBrandFailed(String message) {
        DeviceQueryEvent event = new DeviceQueryEvent(DeviceQueryEvent.RESULT_FAILURE);
        event.setBrandType();
        event.setMessage(message);
        BusProvider.getNetworkInstance().post(event);
    }

    public void getDeviceByBrand(final String brand) {
        String url = URL.URL_GET_DEVICE_BY_BRAND;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("brand", brand);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    // TODO Handler then can't get all brand list
                    getDeviceByBrandFailed(brand, ErrorMessage.CONNECTION_FAILURE);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String message = jsonObject.getString("message");
                        if ("success".equals(message)) {
                            ArrayList<DeviceList> deviceLists = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("device_list");
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject deviceListObject = jsonArray.getJSONObject(i);
                                    DeviceList deviceList = new DeviceList();
                                    deviceList.setName(deviceListObject.getString("series"));
                                    deviceList.setModel(deviceListObject.getString("model"));
                                    deviceLists.add(deviceList);
                                }
                            }
                            Collections.sort(deviceLists, new Comparator<DeviceList>() {
                                public int compare(DeviceList s1, DeviceList s2) {
                                    return s1.getName().compareToIgnoreCase(s2.getName());
                                }
                            });

                            DeviceQueryEvent event = new DeviceQueryEvent(DeviceQueryEvent.RESULT_SUCCESS);
                            event.setNameType();
                            event.setBrand(brand);
                            event.setDeviceList(deviceLists);
                            BusProvider.getNetworkInstance().post(event);
                        } else {
                            getDeviceByBrandFailed(brand, ErrorMessage.SERVER_PROBLEM);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        getDeviceByBrandFailed(brand, ErrorMessage.RESPONSE_FAILURE);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            getDeviceByBrandFailed(brand, ErrorMessage.SERVER_CONNECTION_PROBLEM);
        }
    }

    private void getDeviceByBrandFailed(String brand, String message) {
        DeviceQueryEvent event = new DeviceQueryEvent(DeviceQueryEvent.RESULT_FAILURE);
        event.setMessage(message);
        event.setNameType();
        event.setBrand(brand);
        BusProvider.getNetworkInstance().post(event);
    }

    public void getSubDeviceByName(final String brand, final String name, String model) {
        String url = URL.URL_GET_SUB_DEVICE_BY_NAME;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("brand", brand);
            jsonObject.put("name", name);
            jsonObject.put("model", model);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    // TODO Handler then can't get all brand list
                    getSubDeviceByNameFailed(brand, name, ErrorMessage.CONNECTION_FAILURE);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String message = jsonObject.getString("message");
                        if ("success".equals(message)) {
                            ArrayList<SubDevice> subList = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("device_list");
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String version = jsonArray.getJSONObject(i).getString("version").replace("_", ".");
                                    String fingerprint = jsonArray.getJSONObject(i).getString("fingerprint");
                                    SubDevice subDevice = new SubDevice();
                                    subDevice.setVersion(version);
                                    subDevice.setFingerprint(fingerprint);
                                    subList.add(subDevice);
                                }
                            }
                            Collections.sort(subList, new Comparator<SubDevice>() {
                                public int compare(SubDevice s1, SubDevice s2) {
                                    return s1.getVersion().compareToIgnoreCase(s2.getVersion());
                                }
                            });

                            DeviceQueryEvent event = new DeviceQueryEvent(DeviceQueryEvent.RESULT_SUCCESS);
                            event.setSubType();
                            event.setBrand(brand);
                            event.setSubList(subList);
                            BusProvider.getNetworkInstance().post(event);
                        } else {
                            getSubDeviceByNameFailed(brand, name, ErrorMessage.SERVER_PROBLEM);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        getSubDeviceByNameFailed(brand, name, ErrorMessage.RESPONSE_FAILURE);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            getSubDeviceByNameFailed(brand, name, ErrorMessage.SERVER_CONNECTION_PROBLEM);
        }
    }

    private void getSubDeviceByNameFailed(String brand, String name, String message) {
        DeviceQueryEvent event = new DeviceQueryEvent(DeviceQueryEvent.RESULT_FAILURE);
        event.setMessage(message);
        event.setName(name);
        event.setSubType();
        event.setBrand(brand);
        BusProvider.getNetworkInstance().post(event);
    }

    public void getDeviceInfo(final Context context, final SubDevice subDevice) {
        String url = URL.URL_GET_DEVICE_INFO;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("brand", subDevice.getBrand());
            jsonObject.put("name", subDevice.getName());
            jsonObject.put("model", subDevice.getModel());
            jsonObject.put("version", subDevice.getVersion());
            jsonObject.put("fingerprint", subDevice.getFingerprint());
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            deviceInfoCall = client.newCall(request);
            deviceInfoCall.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    // TODO Handler then can't get all brand list
                    getDeviceInfoFailed(subDevice, ErrorMessage.CONNECTION_FAILURE);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String message = jsonObject.getString("message");
                        if ("success".equals(message)) {
                            String strDeviceInfo = jsonObject.getString("device_info");
                            // TODO DO Something when get data
                            DeviceSwitchEvent event = new DeviceSwitchEvent(DeviceSwitchEvent.EVENT_SUCCESS);
                            event.setSubDevice(subDevice);
                            String fingerprint = subDevice.getFingerprint();
                            FileManager.writeInternalFile(context, StringUtils.wrapUrl(fingerprint) + ".json", strDeviceInfo);
                            BusProvider.getNetworkInstance().post(event);

                        } else {
                            // TODO DO Something failure event
                            getDeviceInfoFailed(subDevice, ErrorMessage.SERVER_PROBLEM);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        getDeviceInfoFailed(subDevice, ErrorMessage.RESPONSE_FAILURE);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            getDeviceInfoFailed(subDevice, ErrorMessage.SERVER_CONNECTION_PROBLEM);
        }
    }

    public void stopDeviceInfoQuery() {
        if(deviceInfoCall != null)
            deviceInfoCall.cancel();
    }

    private void getDeviceInfoFailed(SubDevice subDevice, String message) {
        DeviceSwitchEvent event = new DeviceSwitchEvent(DeviceSwitchEvent.EVENT_FAILURE);
        event.setSubDevice(subDevice);
        event.setMessage(message);
        BusProvider.getNetworkInstance().post(event);
    }
}
