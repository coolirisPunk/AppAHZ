
package com.punkmkt.formula1;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Rect;
        import android.support.annotation.DimenRes;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.ImageLoader;
        import com.android.volley.toolbox.NetworkImageView;
        import com.android.volley.toolbox.StringRequest;
        import com.punkmkt.formula1.adapters.GalleryAdapter;
        import com.punkmkt.formula1.adapters.PilotosAdapter;
        import com.punkmkt.formula1.models.GalleryItem;
        import com.punkmkt.formula1.models.Piloto;
        import com.punkmkt.formula1.models.Premio;
        import com.punkmkt.formula1.utils.AuthRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.w3c.dom.Text;

        import java.util.ArrayList;

public class PilotosDetalleOld extends Activity {
    private String AHZ_PILOTOS_JSON_API_URL = "http://104.236.3.158/api/pilotos/";
    private ArrayList<GalleryItem> galeria_fotos = new ArrayList<GalleryItem>();
    Piloto piloto;
    static ImageLoader imageLoader = MyVolleySingleton.getInstance().getImageLoader();
    private RecyclerView.Adapter adapter;
    static NetworkImageView imagen;
    TextView descripcion_nombre_piloto;
    TextView descripcion_numero_piloto;
    TextView descripcion_nacionalidad_piloto;
    TextView descripcion_fecha_nacimiento_piloto;
    TextView descripcion_lugar_nacimiento_piloto;
    TextView descripcion_campeonatos_piloto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotos_detalle);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        imagen = (NetworkImageView) findViewById(R.id.imagen_principal);
        descripcion_nombre_piloto = (TextView) findViewById(R.id.descripcion_nombre_piloto);
        descripcion_numero_piloto = (TextView) findViewById(R.id.descripcion_numero_piloto);
        descripcion_nacionalidad_piloto = (TextView) findViewById(R.id.descripcion_nacionalidad_piloto);
        descripcion_fecha_nacimiento_piloto = (TextView) findViewById(R.id.descripcion_fecha_nacimiento_piloto);
        descripcion_lugar_nacimiento_piloto = (TextView) findViewById(R.id.descripcion_lugar_nacimiento_piloto);
        descripcion_campeonatos_piloto = (TextView) findViewById(R.id.descripcion_campeonatos_piloto);

        adapter = new GalleryAdapter(galeria_fotos);

        Intent intent = getIntent();
        piloto = new Piloto();

        String id = intent.getStringExtra("id");

        AHZ_PILOTOS_JSON_API_URL = AHZ_PILOTOS_JSON_API_URL + id + "/";


        StringRequest request = new AuthRequest(Request.Method.GET, AHZ_PILOTOS_JSON_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Piloto piloto = new Piloto();
                    piloto.setId(Integer.parseInt(object.optString("id")));
                    piloto.setNombre(object.optString("nombre"));
                    piloto.setNumero(object.optString("numero_piloto"));
                    piloto.setNacionalidad(object.optString("nacionalidad"));
                    piloto.setFecha_nacimiento(object.optString("fecha_nacimiento"));
                    piloto.setLugar_nacimiento(object.optString("lugar_nacimiento"));
                    piloto.setCampeonatos(object.optString("campeonatos"));

                    JSONArray objectarray = object.getJSONArray("fotogaleria_set");
                    for (int count = 0; count < objectarray.length(); count++) {
                        JSONObject anEntry = objectarray.getJSONObject(count);
                        GalleryItem foto = new GalleryItem();
                        foto.setId(Integer.parseInt(anEntry.optString("id")));
                        foto.setThumbnail(anEntry.optString("image_thumbnail"));
                        foto.setImage(anEntry.optString("imagen"));
                        galeria_fotos.add(foto);
                    }
                    createfirstphoto();
                    createdetailpiloto(piloto);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", "Error during request");
                error.printStackTrace();
            }
        });
        MyVolleySingleton.getInstance().addToRequestQueue(request);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //recyclerView.scrollToPosition(0);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pilotos_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void createfirstphoto(){

        Log.d("fotos", galeria_fotos.toString());

        try {
            GalleryItem foto = galeria_fotos.get(0);
            imagen.setImageUrl(foto.getImage(), imageLoader);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
    public void createdetailpiloto(Piloto piloto){
        descripcion_nombre_piloto.setText(piloto.getNombre());
        descripcion_numero_piloto.setText(piloto.getNumero());
        descripcion_nacionalidad_piloto.setText(piloto.getNacionalidad());
        descripcion_fecha_nacimiento_piloto.setText(piloto.getFecha_nacimiento());
        descripcion_lugar_nacimiento_piloto.setText(piloto.getLugar_nacimiento());
        descripcion_campeonatos_piloto.setText(piloto.getCampeonatos());
    }
    public static void updatePhoto(String url){
        imagen.setImageUrl(url, imageLoader);

    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}
