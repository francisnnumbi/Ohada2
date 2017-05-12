package fnn.smirl.comptabilite.gui.adapters;
import android.support.v4.app.*;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.os.Bundle;
 
 
public class TabsAdapter extends FragmentPagerAdapter
{
 
 ArrayList<Fragment> fragmentList;
 public TabsAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList){
	super(fm);
	this.fragmentList = fragmentList;
 }
 
 @Override
 public Fragment getItem(int p1) {
	// TODO: Implement this method
return fragmentList.get(p1);
 }
 
 @Override
 public CharSequence getPageTitle(int position) {
	// TODO: Implement this method
	Fragment f = fragmentList.get(position);
	Bundle b = f.getArguments();
	String s = b.getString("title");
	return s;
 }
 
public void add(Fragment newFragment){
 fragmentList.add(newFragment);
 
 notifyDataSetChanged();
}
 
public void remove(int position){
 fragmentList.remove(position);
 notifyDataSetChanged();
}
 
 @Override
 public int getCount() {
	// TODO: Implement this method
	return fragmentList.size();
 }
 
 
 
}
