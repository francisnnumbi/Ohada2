package fnn.smirl.comptabilite.gui.tabs;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.gui.*;
import fnn.smirl.comptabilite.gui.activities.*;
import fnn.smirl.comptabilite.ohada.operations.*;
import fnn.smirl.comptabilite.ohada.outils.*;
import java.util.*;
import android.widget.*;

public class EcritureTab extends Fragment {
	private Context ctx;
	private EcritureRVAdapter ela;
	private Ecriture selectedEcriture;

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
		RecyclerView rv = (RecyclerView)view.findViewById(R.id.ecrituretab_rv);
		rv.setHasFixedSize(true);

		LinearLayoutManager llm = new LinearLayoutManager(getContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rv.setLayoutManager(llm);


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

		ela = new EcritureRVAdapter(MainActivity.ohada.ecritures);
		rv.setAdapter(ela);
	}

	public void setHeading(String titleHeader) {
		getArguments().putString("title", titleHeader);
	}

	public CharSequence getHeading() {
		return getArguments().getString("title");
	}


	// inner adapter
	class EcritureRVAdapter extends RecyclerView.Adapter<EcritureRVAdapter.DataViewHolder> {

		private List<Ecriture> data;

		public EcritureRVAdapter(List<Ecriture> data) {
			this.data = data;
		}

		@Override
		public EcritureTab.EcritureRVAdapter.DataViewHolder onCreateViewHolder(ViewGroup p1, int p2) {
			// TODO: Implement this method
			View v = LayoutInflater.from(p1.getContext()).
			inflate(R.layout.ecrituretab_model, p1, false);
			DataViewHolder dvh = new DataViewHolder(v);

			return dvh;
		}

		@Override
		public void onBindViewHolder(EcritureTab.EcritureRVAdapter.DataViewHolder p1, int p2) {
			// TODO: Implement this method
			selectedEcriture = data.get(p2);
			try {
				p1.etm_tv_id.setText(selectedEcriture.id + "");
				p1.etm_tv_date.setText(DateConverter.convertToString(selectedEcriture.date, "dd/MM/yyyy"));
				p1.etm_tv_libele.setText(selectedEcriture.libele);
			} catch (Exception eee) {
				//Log.e("lolololol", " -> " + eee.toString());
			}
		}

		@Override
		public int getItemCount() {
			// TODO: Implement this method
			return data.size();
		}

		@Override
		public void onAttachedToRecyclerView(RecyclerView recyclerView) {
			// TODO: Implement this method
			super.onAttachedToRecyclerView(recyclerView);
		}
		
		



		class DataViewHolder extends RecyclerView.ViewHolder {
			CardView cv;
			TextView etm_tv_id, etm_tv_date, etm_tv_libele;

			DataViewHolder(View  view) {
				super(view);
				cv = (CardView)itemView.findViewById(R.id.etm_cv);
				cv.setShadowPadding(0, 0, 10, 10);
				cv.setCardElevation(10);
				cv.setCardBackgroundColor(Color.LTGRAY);

				etm_tv_id = (TextView)view.findViewById(R.id.etm_tv_id);
				etm_tv_date = (TextView)view.findViewById(R.id.etm_tv_date);
				etm_tv_libele = (TextView)view.findViewById(R.id.etm_tv_libele);

				view.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
						// TODO: Implement this method
						MainActivity.ecritureActive = data.get(getPosition());
						Intent	intent = new Intent(getContext(), EcritureActivity.class);
						ctx.startActivity(intent);
					}
				});

				view.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View p1) {
						// TODO: Implement this method
					android.support.v7.widget.PopupMenu popup = new android.support.v7.widget. PopupMenu(getContext(), p1);
						popup.inflate(R.menu.e_popup_menu);
						popup.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener(){

							@Override
							public boolean onMenuItemClick(MenuItem p1) {
								// TODO: Implement this method
								switch (p1.getItemId()) {
									case R.id.e_pop_new:
										MainActivity.ecritureActive = null;
										Intent intent = new Intent(getContext(), EcritureActivity.class);
										ctx.startActivity(intent);
										break;
									case R.id.e_pop_edit:
										MainActivity.ecritureActive = data.get(getPosition());
										intent = new Intent(getContext(), EcritureActivity.class);
										ctx.startActivity(intent);

										break;
									case R.id.e_pop_delete:
										MainActivity.ohada.ecritures.remove(data.get(getPosition()));
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

			}
		}

	}
}
