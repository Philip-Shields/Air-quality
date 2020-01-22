import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
   Air quality is a program that reads the JSON stream from AQI.
   This application is basically a fun experiment
   Air quality  Copyright (C) 2019  Philip Shields: shields_phil@yahoo.com.au.
      
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
    
 */

public class AirQualitySimple {	
	
	//http://aqicn.org/json-api/doc/
	static String apiKey="0000000000000000000000000";//my key
	static String coOrdinates="-36.05035;146.942"; //southern hemisphere coordinates
	
	static String airQuality;	
	static int aqiNumber;
	static String aqiHuman;

	public static void main(String[] args) throws IOException {
		
		JsonAirQuality();
	}
	
public static void JsonAirQuality() throws IOException{
	
	
	//constructors
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		Aqi aqi = new Aqi();        
		   
	    Data data = new Data();
	   
	    aqi.setData(data);  
	   
	    URL url = new URL("https://api.waqi.info/feed/geo: "+coOrdinates+"/?token="+apiKey); 
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
	   
	    InputStreamReader reader = new InputStreamReader(conn.getInputStream()); //read JSON stream
	    Aqi aqiObject = gson.fromJson(reader, Aqi.class);
	   
	    	reader.close();		
			
			aqiNumber=Integer.parseInt(aqiObject.data.getAqi());		
	        aqiHuman=ReadableAqi(Integer.parseInt(aqiObject.data.getAqi()));
	         

	        //System.out.println(gson.toJson(aqiObject));
			
			System.out.println("Air quality= "+aqiObject.data.getAqi());
		       System.out.println("Name= "+aqiObject.data.city.getName());
		       System.out.println("Coordinates= "+aqiObject.data.city.getGeo());
		       System.out.println("Rating= "+ReadableAqi(Integer.parseInt(aqiObject.data.getAqi())));
		       System.out.println("Time= "+aqiObject.data.time.getS());
		       
		      

	}
//human readable air quality catagories
public static String ReadableAqi(int aqiRating) {
	String rating="";
	
	if (aqiRating >=0 && aqiRating <=50) {
		rating="Good";
	
	}
	
	if (aqiRating >=51 && aqiRating <=100) {
		rating="Moderate";
	
	}

	if (aqiRating >=101 && aqiRating <=150) {
		rating="Unhealthy for sensitive people";
	
	}

	if (aqiRating >=151 && aqiRating <=200) {
		rating="Unhealthy";
	
	}

	if (aqiRating >=201 && aqiRating <=300) {
		rating="Very Unhealthy";
	
	}

	if (aqiRating >=301 && aqiRating <=500) {
		rating="Hazardous";
	
	}

		return rating;
		
}


	
//example json from aqi site see: http://aqicn.org/json-api/doc/
  /**
 
   {
"status":"ok",
"data":{"aqi":73,
"idx":3273,
"attributions":[{"url":"https://www.environment.nsw.gov.au/",
"name":"Office of Environment and Heritage - NSW",
"logo":"Australia-NSW.png"},
{"url":"https://waqi.info/",
"name":"World Air Quality Index Project"}],

"city":{"geo":[-36.05035,146.942],
"name":"Albury South-west Slopes, Australia",
 "url":"https://aqicn.org/city/australia/nsw/albury/south-west-slopes"},
 
 "dominentpol":"pm25",
 "iaqi":{
 "pm10":{
 "v":41
 },
 "pm25":{
 "v":73
 },
 
 "t":{
 "v":34.4
 }},
 
 "time":{"s":"2020-01-16 13:00:00",
 "tz":"+11:00",
 "v":1579179600},
 
 "debug":{"sync":"2020-01-16T13:04:08+09:00"}
 
 }
    
}
*/

public static class Aqi {
	private String status;	
	private Data data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
}



public static class Data {
	private String aqi;
	private String idx;
	private Attributions[] attributions;
	private City city;
	private String dominentpol;
	private Iaqi iaqi;
	private Time time;
	private Debug debug;
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public Attributions[] getAttributions() {
		return attributions;
	}
	public void setAttributions(Attributions[] attributions) {
		this.attributions = attributions;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getDominentpol() {
		return dominentpol;
	}
	public void setDominentpol(String dominentpol) {
		this.dominentpol = dominentpol;
	}
	public Iaqi getIaqi() {
		return iaqi;
	}
	public void setIaqi(Iaqi iaqi) {
		this.iaqi = iaqi;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public Debug getDebug() {
		return debug;
	}
	public void setDebug(Debug debug) {
		this.debug = debug;
	}
	
	
	
}

public static class Attributions {
	private String url;
	private String name;
	private String logo;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}

public static class City {
	private List<Double> geo;
	private String name;
	private String url;
	public List<Double> getGeo() {
		return geo;
	}
	public void setGeo(List<Double> geo) {
		this.geo = geo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}

public static class Geo {
	
	}	


public static class Debug {
	private String sync;

	public String getSync() {
		return sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}
}

public static class Iaqi {
	private Pm10 pm10;
	private Pm25 pm25;
	private T t;
	public Pm10 getPm10() {
		return pm10;
	}
	public void setPm10(Pm10 pm10) {
		this.pm10 = pm10;
	}
	public Pm25 getPm25() {
		return pm25;
	}
	public void setPm25(Pm25 pm25) {
		this.pm25 = pm25;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
	
	
}

public static class Pm10 {
	
	private String v;

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}
}

public static class Pm25 {
	
	private String v;

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}
}
public static class T {
	
	private String v;

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}
}

public static class Time {
	private String s;
	private String tz;
	private String v;
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public String getTz() {
		return tz;
	}
	public void setTz(String tz) {
		this.tz = tz;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	
	
	
}



	
	
	}
	







