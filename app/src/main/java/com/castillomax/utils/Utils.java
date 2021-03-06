package com.castillomax.utils;

import android.content.Context;
import com.castillomax.navigationliveo.R;

public class Utils {

	//Set all the navigation icons and always to set "zero 0" for the item is a category
	public static int[] iconNavigation = new int[] { 
		0,
        R.drawable.ic_action_about,
        0,
        R.drawable.ic_action_call,
        R.drawable.ic_action_link,
		R.drawable.ic_action_map,
        };
	
	//get title of the item navigation
	public static String getTitleItem(Context context, int posicao){		
		String[] titulos = context.getResources().getStringArray(R.array.nav_menu_items);  
		return titulos[posicao];
	} 
	
}
