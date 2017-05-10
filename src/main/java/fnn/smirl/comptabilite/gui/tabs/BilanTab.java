package fnn.smirl.comptabilite.gui.tabs;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.R;
import fnn.smirl.comptabilite.gui.*;
import android.content.*;
import fnn.smirl.comptabilite.ohada.operations.*;
import java.util.*;
import android.view.View.*;
import android.util.*;
import fnn.smirl.comptabilite.gui.activities.*;
import fnn.smirl.comptabilite.ohada.outils.*;
import fnn.smirl.comptabilite.gui.adapters.*;
import fnn.smirl.comptabilite.ohada.groupes.*;

public class BilanTab extends Fragment {
	private Context ctx;


	public BilanTab() {
		Bundle b = new Bundle();
		b.putString("title", "RESUMÉS");
		setArguments(b);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: Implement this method
		return inflater.inflate(R.layout.resumetab, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		ctx = view.getContext();
		
		Button bilan_btn = (Button)view.findViewById(R.id.resumetab_bilan_btn);
		bilan_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				startActivity(new Intent(ctx, EtatFinActivity.class));
			}
		});
		
	}

	public void setHeading(String titleHeader) {
		getArguments().putString("title", titleHeader);
	}

	public CharSequence getHeading() {
		return getArguments().getString("title");
	}

	// inner adapter
	
}
