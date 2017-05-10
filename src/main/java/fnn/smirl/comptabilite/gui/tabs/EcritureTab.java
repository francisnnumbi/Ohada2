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

public class EcritureTab extends Fragment {
	private ListView cptList;
	private Context ctx;
	private EcritureListAdapter ela;

	public EcritureTab() {
		//ctx = MainActivity.ctx;
		Bundle b = new Bundle();
		b.putString("title", "JOURNALISATION");
		setArguments(b);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: Implement this method
		return inflater.inflate(R.layout.ecrituretab, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
	ctx = view.getContext();
		Button ecriture_add_btn = (Button)view.findViewById(R.id.ecriture_add_btn);
		ecriture_add_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				MainActivity.ecritureActive = null;
				Intent intent = new Intent(ctx, EcritureActivity.class);
				ctx.startActivity(intent);
			}
		});
		cptList = (ListView)view.findViewById(R.id.ecrituretab_lv);

		ela = new EcritureListAdapter(MainActivity.ohada.ecritures);
		cptList.setAdapter(ela);
	}

	public void setHeading(String titleHeader) {
		getArguments().putString("title", titleHeader);
	}

	public CharSequence getHeading() {
		return getArguments().getString("title");
	}

	// inner adapter
	class EcritureListAdapter extends ArrayAdapter<Ecriture> {

		public EcritureListAdapter(ArrayList<Ecriture> list) {
			super(ctx, 0, list);
			setNotifyOnChange(true);
		}

		@Override
		public View getView(int position, View convertView, final ViewGroup parent) {
			// TODO: Implement this method
			final Ecriture cpt = getItem(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.ecrituretab_model, parent, false);
			}
			TextView etm_tv_id = (TextView)convertView.findViewById(R.id.etm_tv_id);
			TextView etm_tv_date = (TextView)convertView.findViewById(R.id.etm_tv_date);
			TextView etm_tv_libele = (TextView)convertView.findViewById(R.id.etm_tv_libele);

			try {
				etm_tv_id.setText(cpt.id + "");
				etm_tv_date.setText(DateConverter.convertToString(cpt.date, "dd/MM/yyyy"));
				etm_tv_libele.setText(cpt.libele);
			} catch (Exception eee) {
				Log.e("lolololol", cpt.libele + " -> " + eee.toString());
			}

			convertView.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1) {
					// TODO: Implement this method
					PopupMenu popup = new PopupMenu(parent.getContext(), p1);
					popup.inflate(R.menu.e_popup_menu);
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

						@Override
						public boolean onMenuItemClick(MenuItem p1) {
							// TODO: Implement this method
							switch (p1.getItemId()) {
								case R.id.e_pop_new:
									MainActivity.ecritureActive = null;
									Intent intent = new Intent(parent.getContext(), EcritureActivity.class);
									ctx.startActivity(intent);
									break;
								case R.id.e_pop_edit:
									MainActivity.ecritureActive = cpt;
									intent = new Intent(parent.getContext(), EcritureActivity.class);
									ctx.startActivity(intent);

									break;
								case R.id.e_pop_delete:
									MainActivity.ohada.ecritures.remove(cpt);
									MainActivity.updateData();
									ela.notifyDataSetChanged();
									break;
							}
							return false;
						}
					});

					popup.show();
					return false;
				}

			});
			return convertView;
		}



	}
}
