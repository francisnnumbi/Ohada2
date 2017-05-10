package fnn.smirl.comptabilite.gui.activities;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.ohada.enums.classes.*;

public class ReferencesActivity extends ExpandableListActivity {
	ClassesRef classesRef = new ClassesRef();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		getExpandableListView().setDividerHeight(1);
		getExpandableListView().setAdapter(new MyExpandableListAdapter());

	}

	
	class MyExpandableListAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			// TODO: Implement this method
			return classesRef.classeDef().size();
		}

		@Override
		public int getChildrenCount(int p1) {
			// TODO: Implement this method
			return classesRef.classeDef().get(p1).size();
		}

		@Override
		public ClasseN getGroup(int p1) {
			// TODO: Implement this method
			return classesRef.classeDef().get(p1);
		}

		@Override
		public String getChild(int p1, int p2) {
			// TODO: Implement this method
			ClasseN map = classesRef.classeDef().get(p1);
			return map.getAt(p2);
		}

		@Override
		public long getGroupId(int p1) {
			// TODO: Implement this method
			return p1;
		}

		@Override
		public long getChildId(int p1, int p2) {
			// TODO: Implement this method
			return p2;
		}

		@Override
		public boolean hasStableIds() {
			// TODO: Implement this method
			return true;
		}

		@Override
		public View getGroupView(int p1, boolean p2, View view, ViewGroup p4) {
			// TODO: Implement this method
			ClasseN cn = getGroup(p1);
			if (view == null) {
				view = LayoutInflater.from(getApplicationContext()).
				inflate(R.layout.ref_group, null);
			}
			TextView tv1 = (TextView)view.findViewById(R.id.ref_group_tv1);
			tv1.setText(cn.getLabel());

			return view;
		}

		@Override
		public View getChildView(int p1, int p2, boolean p3, View view, ViewGroup p5) {
			// TODO: Implement this method
			String txt = getChild(p1, p2);
			if (view == null) {
				view = LayoutInflater.from(getApplicationContext()).
				inflate(R.layout.ref_child, null);
			}

			TextView tv1 = (TextView)view.findViewById(R.id.ref_child_tv1);
			tv1.setText(txt);
			return view;
		}

		@Override
		public boolean isChildSelectable(int p1, int p2) {
			// TODO: Implement this method
			return true;
		}


	}
}
