package fnn.smirl.comptabilite.gui.tabs;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.R;
import fnn.smirl.comptabilite.gui.*;

public class TousComptesTab extends Fragment {
	ListView cptList;
	TextView tv1;
	public TousComptesTab() {
		Bundle b = new Bundle();
		b.putString("title", "GRAND LIVRE");
		setArguments(b);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: Implement this method
		return inflater.inflate(R.layout.touscomptestab, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		cptList = (ListView)view.findViewById(R.id.touscomptestab_lv);
		cptList.setAdapter(MainActivity.cpt_list_adapter);
	}

	public void setHeading(String titleHeader) {
		getArguments().putString("title", titleHeader);
	}

	public CharSequence getHeading() {
		return getArguments().getString("title");
	}
}
