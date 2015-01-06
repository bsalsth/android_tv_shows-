package com.toolsforyoutube.utils;

import com.toolsforyoutube.mainacitivity.SearchResult;

public class CustomFunctions {

	public static String getNextSearchPage(String order, String duration,String vtype,String searchField, String key ,String prevKey){
		String searchFieldFinal = "";
		String search = "";
		if(searchField.contains(" ")){
			search =  searchField.replace(" ","+");
			searchFieldFinal = search;
		}else{
			searchFieldFinal = searchField;
		}
		String next = "https://www.googleapis.com/youtube/v3/search?"+
				"order="+order+
				"&part=snippet%2Cid"+
				"&q="+searchFieldFinal+
				"&type=video"+
				"&videoType="+vtype+
				"&videoDuration="+duration+
				"&pageToken="+prevKey+
				"&maxResults=10"+
				"&key="+key;

		return next;
	}

	public static String getSearchApi(String order, String duration,String vtype,String searchField, String key){
		String searchFieldFinal = "";
		String search = "";
		if(searchField.contains(" ")){
			search =  searchField.replace(" ","+");
			searchFieldFinal = search;
		}else{
			searchFieldFinal = searchField;
		}
		String result = "https://www.googleapis.com/youtube/v3/search?"+
				"order="+order+
				"&part=snippet%2Cid"+
				"&q="+searchFieldFinal+
				"&type=video"+
				"&videoType="+vtype+
				"&videoDuration="+duration+
				"&maxResults=7"+
				"&key="+key;
		return result;
	}

	public static String converDate(String date){
		String finalDate="";
		if(date.contains("T")){
			String[] parts = date.split("T");
			finalDate = parts[0];
		}
		return finalDate;
	}

	public static String convertTime (String string){
		//	Variables for video duration
		String word,word1,word2="",word3,hour="",min="",sec="",readySplit="",finalValue="";
		word = string;

		//replacing character in this String
		word1 = word.replace("PT", "");

		if (string!=null){
			if(word.contains("H")){
				word2 = word1.replace("H", "-"); 
				word3 = word2.replace("M", "-");
				readySplit = word3.replace("S", "");
			}else if(word.contains("M")&&word.contains("H")==false){
				word2 = word1.replace("M", "-");
				readySplit = word2.replace("S", "");
			}else if(word.contains("M")==false){
				//			readySplit = word2.replace("S", "");
				readySplit =word1;
			}
		}



		if(readySplit.contains("-")){
			String[] parts = readySplit.split("-");

			if(word.contains("H")){
				hour = parts[0]; // 004
				min = parts[1]; // 034556
				sec = parts[2]; // 034556
				finalValue = hour+":"+min+":"+sec;
			}
			else if (word.contains("M")&&word.contains("H")==false) {
				hour = null;
				min = parts[0]; // 034556
								sec = parts[1]; // 034556
								finalValue =min+":"+sec;
			}}else {
				//				hour = null;
				//				min = null;
				sec = readySplit; // 034556
				finalValue = sec;
			}

		return finalValue;
	}
}
