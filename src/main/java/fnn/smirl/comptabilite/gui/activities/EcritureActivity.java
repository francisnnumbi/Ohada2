package fnn.smirl.comptabilite.gui.activities;
import android.support.v7.app.*;
import android.os.*;
import fnn.smirl.comptabilite.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.app.*;
import java.util.*;
import fnn.smirl.comptabilite.ohada.outils.*;
import fnn.smirl.comptabilite.ohada.operations.*;
import fnn.smirl.comptabilite.gui.*;
import android.content.*;
import android.util.*;

public class EcritureActivity extends AppCompatActivity {
	EditText date_ed;
	long e_date = 0;
	ArrayList<Entree> ale;
	EntreeListAdapter el_adapter;
	EditText	id_ed;
	boolean wasNew = false;
	EditText	libele_ed;
	public static Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecriture);
		ctx = getApplicationContext();
		Calendar cal = Calendar.getInstance();

		id_ed = (EditText)findViewById(R.id.ecriture_id);
		libele_ed = (EditText)findViewById(R.id.ecriture_libelle);
		final	CheckBox equil_check = (CheckBox)findViewById(R.id.ecriture_equilibre);

		date_ed = (EditText)findViewById(R.id.ecriture_date);
		date_ed.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				setDate();
			}
		});

		if (MainActivity.ecritureActive != null) {
			wasNew = false;
			id_ed.setText(MainActivity.ecritureActive.id + "");
			libele_ed.setText(MainActivity.ecritureActive.libele);
			e_date = MainActivity.ecritureActive.date;
			date_ed.setText(DateConverter.convertToString(e_date, "dd/MM/yyyy"));
			equil_check.setChecked(MainActivity.ecritureActive.equilibre());
		} else {
			wasNew = true;
			id_ed.setText(cal.get(Calendar.YEAR)+ "");
			MainActivity.ecritureActive = new Ecriture(MainActivity.ohada.ecritures.size(),
			e_date, libele_ed.getText().toString());
		}


		Button add_btn_ed = (Button)findViewById(R.id.ecriture_add_btn);
		add_btn_ed.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				saveEcriture();
				addEntry();
				updateEntreeList();
			}	
		});

		Button verify_btn_ed = (Button)findViewById(R.id.ecriture_verify_btn);
		verify_btn_ed.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				equil_check.setChecked(MainActivity.ecritureActive.equilibre());
			}	
		});



		Button save_btn_ed = (Button)findViewById(R.id.ecriture_save_btn);
		save_btn_ed.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				saveEcriture();
				finish();
			}	
		});

		ListView ecriture_list = (ListView)findViewById(R.id.ecriture_list);
		ale = MainActivity.ecritureActive.entrees();
		el_adapter = new EntreeListAdapter(ale);
		ecriture_list.setAdapter(el_adapter);


	}

	private void updateEntreeList() {
		ale = MainActivity.ecritureActive.entrees();
		el_adapter.notifyDataSetChanged();
	}

	//Date dialog
	private void setDate() {
		final Dialog dateDialog = new Dialog(this);
		dateDialog.setContentView(R.layout.date_dialog);
		dateDialog.setTitle("Choisir Date");
		final DatePicker dd_date = (DatePicker)dateDialog.findViewById(R.id.datedialog_date);

		Button ok_btn = (Button)dateDialog.findViewById(R.id.datedialog_ok_btn);
		ok_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				Calendar cal = Calendar.getInstance();
				cal.clear();
				cal.set(dd_date.getYear(), dd_date.getMonth(), dd_date.getDayOfMonth());
				e_date = cal.getTimeInMillis();
				date_ed.setText(DateConverter.convertToString(e_date, "dd/MM/yyyy"));
				dateDialog.dismiss();
			}		
		});

		Button cancel_btn = (Button)dateDialog.findViewById(R.id.datedialog_cancel_btn);
		cancel_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				dateDialog.dismiss();
			}
		});

		dateDialog.show();
	}

	//entry dialog
	private void addEntry() {
		final Dialog entreeDialog = new Dialog(this);
		entreeDialog.setContentView(R.layout.entree_dialog);
		entreeDialog.setTitle("Entrée");

		final EditText ac_titre = (EditText)entreeDialog.findViewById(R.id.entreedialog_ac_titre);
		final EditText ac_montant = (EditText)entreeDialog.findViewById(R.id.entreedialog_montant);

		final Spinner ac_spinner = (Spinner)entreeDialog.findViewById(R.id.entreedialog_ac_id);
		ArrayAdapter<Integer> ac_id_adapter = new ArrayAdapter<Integer>(this, R.layout.drop_down_model, MainActivity.ohada.compteIds());
		ac_spinner.setAdapter(ac_id_adapter);
		ac_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
				// TODO: Implement this method
				ac_titre.setText(MainActivity.ohada.compteTitre(p1.getSelectedItem()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> p1) {
				// TODO: Implement this method
			}


		});

		final Spinner cote_spinner = (Spinner)entreeDialog.findViewById(R.id.entreedialog_cote);
		ArrayList<String>	cote_arr = new ArrayList<String>();
		cote_arr.add("DEBIT");
		cote_arr.add("CREDIT");
		ArrayAdapter<String> cote_adapter = new ArrayAdapter<String>(this, R.layout.drop_down_model, cote_arr);
		cote_spinner.setAdapter(cote_adapter);

		Button ok_btn = (Button)entreeDialog.findViewById(R.id.entreedialog_ok_btn);
		ok_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				Entree.Cote coo = Entree.Cote.DEBIT;
				if (cote_spinner.getSelectedItem().equals("DEBIT")) {
					coo = Entree.Cote.DEBIT;
				} else {
					coo = Entree.Cote.CREDIT;
				}

				MainActivity.ecritureActive.ajoute(MainActivity.ohada.compte(ac_spinner.getSelectedItem()),
				Double.parseDouble(ac_montant.getText().toString()), coo);	
				updateEntreeList();
				entreeDialog.dismiss();
			}		
		});

		entreeDialog.show();
	}

	// inner adapter
	class EntreeListAdapter extends ArrayAdapter<Entree> {

		public EntreeListAdapter(ArrayList<Entree> list) {
			super(ctx, 0, list);
			setNotifyOnChange(true);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO: Implement this method
			final Entree entreem = getItem(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(ctx)
				.inflate(R.layout.entree_model, parent, false);
			}
			TextView em_tv_id = (TextView)convertView.findViewById(R.id.entreemodel_ac_id);
			TextView em_tv_titre = (TextView)convertView.findViewById(R.id.entreemodel_ac_titre);
			TextView em_tv_amount = (TextView)convertView.findViewById(R.id.entreemodel_montant);
			TextView em_tv_cote = (TextView)convertView.findViewById(R.id.entreemodel_cote);

			try {
				em_tv_id.setText(entreem.cptId + "");
				em_tv_titre.setText(MainActivity.ohada.compteTitre(entreem.cptId));
				em_tv_amount.setText(entreem.montant + "");
				em_tv_cote.setText(entreem.cote.name());
			} catch (Exception eee) {
				Log.e("EcritureActivity", eee.toString());
			}

			convertView.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1) {
					// TODO: Implement this method
					PopupMenu popup = new PopupMenu(ctx, p1);
					popup.inflate(R.menu.ee_popup_menu);
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

						@Override
						public boolean onMenuItemClick(MenuItem p1) {
							// TODO: Implement this method
							switch (p1.getItemId()) {
								case R.id.ee_pop_new:
									addEntry();
									updateEntreeList();
									break;
								case R.id.ee_pop_edit:


									break;
								case R.id.ee_pop_delete:
									MainActivity.ecritureActive.efface(entreem);
									updateEntreeList();
									MainActivity.updateData();
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

		private void saveEcriture() {
			if (wasNew) {
				MainActivity.ecritureActive.met(Integer.parseInt(id_ed.getText().toString()), e_date, libele_ed.getText().toString());
				MainActivity.ohada.ecritures.add(MainActivity.ecritureActive);
				wasNew = false;
			} else {
				MainActivity.ecritureActive.id = Integer.parseInt(id_ed.getText().toString());
				MainActivity.ecritureActive.date = e_date;
				MainActivity.ecritureActive.libele = libele_ed.getText().toString();
			}
			MainActivity.updateData();
		}

	
}
