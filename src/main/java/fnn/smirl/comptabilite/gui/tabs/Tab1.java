package fnn.smirl.comptabilite.gui.tabs;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import fnn.smirl.comptabilite.*;

public class Tab1 extends Fragment
{
String heading = "";
int mNum;
 TextView tv1;
 public Tab1(){}
 
 public static Tab1 getInstance(int num){
	Tab1 t1 = new Tab1();
	Bundle b = new Bundle();
	b.putInt("num", num);
	t1.setArguments(b);
	t1.heading = "Tab "+ num;
	return t1;
 }
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	// TODO: Implement this method
	return inflater.inflate(R.layout.tab1, container, false);
 }

 @Override
 public void onViewCreated(View view, Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onViewCreated(view, savedInstanceState);
	mNum = getArguments() != null? getArguments().getInt("num"):1;
	tv1 = (TextView)view.findViewById(R.id.tv1);
	
	tv1.setText(heading);
 }

 public void setHeading(String titleHeader){
	heading = titleHeader;
 }
 
 public CharSequence getHeading(){
	return heading;
 }
 
}
