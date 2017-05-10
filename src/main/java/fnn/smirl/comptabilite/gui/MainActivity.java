package fnn.smirl.comptabilite.gui;

import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.gui.activities.*;
import fnn.smirl.comptabilite.gui.adapters.*;
import fnn.smirl.comptabilite.gui.tabs.*;
import fnn.smirl.comptabilite.ohada.*;
import fnn.smirl.comptabilite.ohada.operations.*;
import fnn.smirl.comptabilite.ohada.plan.*;
import fnn.smirl.simple.*;
import java.io.*;
import java.util.*;


public class MainActivity extends AppCompatActivity
implements OnClickListener {

	static File sdcard = Environment.getExternalStorageDirectory();
	static File dir = new File(sdcard.getAbsolutePath() + "/Android/data/fnn.smirl.comptabilite/ohada/db");
	static File filename = new File(dir, "/ohada.json");
	static Serializer serializer = new Serializer();
	public static Ohada ohada = new Ohada();
	public static Compte compteActif = null;
	public static Ecriture ecritureActive = null;
	public static Context ctx;
	FloatingActionButton fab;
	TabLayout tabLayout;
	ViewPager my_pager;
	private static TabsAdapter tad;
	public static ComptesListAdapter cpt_list_adapter;
	int index = 0;
	ArrayList<Fragment> tabarray = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ctx = this;
		init();	
	}

	@Override
	protected void onResume() {
		// TODO: Implement this method
		super.onResume();
		cpt_list_adapter.notifyDataSetChanged();
	}

	private void init() {
		if (!dir.exists())dir.mkdirs();
		if (cpt_list_adapter == null) {
			try {
				retrieve();
				cpt_list_adapter = new ComptesListAdapter(this, ohada.comptes);
			} catch (Exception e) {
				ohada = new Ohada();
				store();
			} finally {
				cpt_list_adapter = new ComptesListAdapter(this, ohada.comptes);
			}

		}
		setupToolbar();
		setTabLayout();
		setupViewPager();
		setupFab();
	}

	private void setupToolbar() {
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		// Show menu icon
		final ActionBar ab = getSupportActionBar();
		ab.setHomeAsUpIndicator(R.drawable.ic_menu);
		ab.setDisplayHomeAsUpEnabled(true);
	}

	private void setupFab() {
		fab = (FloatingActionButton)findViewById(R.id.fab);
		fab.setOnClickListener(this);
	}

	private void setTabLayout() {
		tabLayout = (TabLayout)findViewById(R.id.tabLayout);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

	}

	private void setupViewPager() {
		my_pager = (ViewPager)findViewById(R.id.my_pager);
		TousComptesTab tct = new TousComptesTab();
		tabarray.add(tct);
		EcritureTab ect = new EcritureTab();
		tabarray.add(ect);
		BilanTab bilt = new BilanTab();
		tabarray.add(bilt);
		tad = new TabsAdapter(getSupportFragmentManager(), tabarray);
		my_pager.setAdapter(tad);
		tabLayout.setupWithViewPager(my_pager);
	}
	
	public static  void effacerCompte(int index){
		ohada.effacerCompte(index);
	updateData();
	}

	public static void updateData() {
		store();
		tad.notifyDataSetChanged();
	}

	private static void store() {
		//showSnack("Mise à jour de la base des données en cours...");
		serializer.serialize(filename.getAbsolutePath(), ohada, Ohada.class);
		//showSnack("Mise à jour terminée.");
	}

	private static void retrieve() {
		ohada = serializer.deserialize(filename.getAbsolutePath(), Ohada.class);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO: Implement this method
		switch (item.getItemId()) {
			case R.id.action_add:
				startCompteActivity(null);
				break;
			case R.id.action_about:
				startActivity(new Intent(this, ReferencesActivity.class));
				break;
			case R.id.action_exit:
				finish();
				break;
				
		}
		return true;
	}

	@Override
	public void onClick(View p1) {
		if (p1.getId() == R.id.fab) {
			startCompteActivity(null);
		}
	}

	private void showSnack(String msg) {
		Snackbar.make(findViewById(R.id.coordinatorLayout),
		msg,
		Snackbar.LENGTH_SHORT)
		.setAction("OK", this)
		.show();
	}

	public static void startCompteActivity(Compte cpt) {
		Intent intent = new Intent(ctx, CompteActivity.class);
		if (cpt == null) {
			compteActif = null;
		} else {
			compteActif = cpt;
			intent.putExtra("id", cpt.id());
		}
		ctx.startActivity(intent);
	}
}
