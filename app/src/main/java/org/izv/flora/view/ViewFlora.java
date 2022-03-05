package org.izv.flora.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class ViewFlora extends AppCompatActivity {
    private TextView tvFamiliaDetalles, tvIdentificacionDetalles, tvAltitudDetalles, tvHabitatDetalles, tvFitosociologiaDetalles, tvBiotipoDetalles,
            tvBiologiaReproductivaDetalles, tvFloracionDetalles, tvFructificacionDetalles, tvExpresionAsexualDetalles, tvPolinizacionDetalles,
            tvDispersionDetalles, tvNumeroCromosomaticoDetalles, tvReproduccionAsexualDetalles, tvDistribucionDetalles, tvBiologiaDetalles, tvDemografiaDetalles,
            tvAmenazasDetalles, tvMedidasPropuestasDetalles, tvNombreDetalles;
    private Flora flora;
    private FloatingActionButton fabTrash, fabEdit, fabAddImageView, fabMenu;
    private Boolean isFABOpen = false;
    private MainActivityViewModel mavm;
    private Object[] imagenes;
    private ImageSlider sliderViewFlora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flora);
        initialize();
    }

    private void initialize() {
        setComponents();
        fillComponents();


        fabMenu.setOnClickListener(view -> {
            if(!isFABOpen){
                fabMenu.setImageResource(R.drawable.ic_baseline_menu_open_24);
                showFABMenu();
            }else{
                fabMenu.setImageResource(R.drawable.ic_baseline_menu_24);
                closeFABMenu();
            }
        });

        fabTrash.setOnClickListener(view -> dialogo());

        fabEdit.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("flora", flora);
            Intent i = new Intent(getApplicationContext(), AddFloraActivity.class);
            i.putExtras(bundle);
            startActivity(i);
        });

        fabAddImageView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("flora", flora);
            Intent i = new Intent(getApplicationContext(), AddImagenActivity.class);
            i.putExtras(bundle);
            startActivity(i);
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mavm.getFlora(flora.getId());
        mavm.getOneFlora().observe(this, flora2 -> {
            flora = flora2;
            fillComponents();
        });

    }

    private void cargarSlider() {
        mavm.getImagenes(flora.getId());
        mavm.getImagenesLiveData().observe(this, imagens -> {
            imagenes = imagens.toArray();
            if (imagenes.length <= 0) {
                datosSlider();
            }else {
                datosSlider(imagenes);
            }
        });
    }

    private void datosSlider() {
        ArrayList<SlideModel> imagenes = new ArrayList<>();
        String url = "https://informatica.ieszaidinvergeles.org:10009/ad/felixRDLFApp/public/api/imagen/" + flora.getId() + "/flora";
        imagenes.add(new SlideModel(url, null, ScaleTypes.FIT));
        
        sliderViewFlora.setImageList(imagenes);
    }

    private void datosSlider(Object[] imgs) {
        ArrayList<SlideModel> imagenes = new ArrayList<>();
        String url;
        for (int i = 0; i < imgs.length; i++) {
            url = "https://informatica.ieszaidinvergeles.org:10009/ad/felixRDLFApp/public/api/imagen/" + imgs[i];
            imagenes.add(new SlideModel(url, null, ScaleTypes.CENTER_CROP));
        }
        sliderViewFlora.setImageList(imagenes);
    }


    private void showFABMenu(){
        isFABOpen=true;
        fabEdit.animate().translationY(-125);
        fabAddImageView.animate().translationY(-215);
        fabTrash.animate().translationY(-305);
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabEdit.animate().translationY(0);
        fabTrash.animate().translationY(0);
        fabAddImageView.animate().translationY(0);
    }

    private void dialogo() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Configura el titulo.
        alertDialogBuilder.setTitle("Eliminar Flora");

        // Configura el mensaje.
        alertDialogBuilder
                .setMessage("Estas seguro que deseas borrar \""+ flora.getNombre() +"\"?")
                .setCancelable(false)
                .setPositiveButton("Si", (dialog, id) -> {
                    //Eliminamos la flora
                    eliminarFlora();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    //No se elimina la flora
                    dialog.cancel();
                }).create().show();
    }

    private void eliminarFlora() {
        mavm.deleteFlora(flora.getId());
        mavm.getDeleteLiveData().observe(this, row -> {
            if(row < 0){
                Toast.makeText(getApplicationContext(),"No se ha podido borrar la flora",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Flora borrada con exito",Toast.LENGTH_SHORT).show();
            }
            //Se haya eliminado o no se sale de la pantalla
            finish();
        });
    }

    private void setComponents() {
        flora = getIntent().getExtras().getParcelable("flora");

        tvNombreDetalles = findViewById(R.id.tvNombreDetalles);

        sliderViewFlora = findViewById(R.id.sliderViewFlora);

        tvFamiliaDetalles = findViewById(R.id.tvFamiliaDetalles);
        tvIdentificacionDetalles = findViewById(R.id.tvIdentificacionDetalles);
        tvAltitudDetalles = findViewById(R.id.tvAltitudDetalles);
        tvHabitatDetalles = findViewById(R.id.tvHabitatDetalles);
        tvFitosociologiaDetalles = findViewById(R.id.tvFitosociologiaDetalles);
        tvBiotipoDetalles = findViewById(R.id.tvBiotipoDetalles);
        tvBiologiaReproductivaDetalles = findViewById(R.id.tvBiologiaReproductivaDetalles);
        tvFloracionDetalles = findViewById(R.id.tvFloracionDetalles);
        tvFructificacionDetalles = findViewById(R.id.tvFructificacionDetalles);
        tvExpresionAsexualDetalles = findViewById(R.id.tvExpresionAsexualDetalles);
        tvPolinizacionDetalles = findViewById(R.id.tvPolinizacionDetalles);
        tvDispersionDetalles = findViewById(R.id.tvDispersionDetalles);
        tvNumeroCromosomaticoDetalles = findViewById(R.id.tvNumeroCromosomaticoDetalles);
        tvReproduccionAsexualDetalles = findViewById(R.id.tvReproduccionAsexualDetalles);
        tvDistribucionDetalles = findViewById(R.id.tvDistribucionDetalles);
        tvBiologiaDetalles = findViewById(R.id.tvBiologiaDetalles);
        tvDemografiaDetalles = findViewById(R.id.tvDemografiaDetalles);
        tvAmenazasDetalles = findViewById(R.id.tvAmenazasDetalles);
        tvMedidasPropuestasDetalles = findViewById(R.id.tvMedidasPropuestasDetalles);

        fabMenu = findViewById(R.id.fabMenu);
        fabEdit = findViewById(R.id.fabEdit);
        fabTrash = findViewById(R.id.fabDeleteFloraDetalles);
        fabAddImageView = findViewById(R.id.fabAddImageView);

        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    private void fillComponents() {
        cargarSlider();

        tvNombreDetalles.setText(flora.getNombre());

        tvFamiliaDetalles.setText(flora.getFamilia());
        if(tvFamiliaDetalles.getText().toString().isEmpty()) tvFamiliaDetalles.setText("<No especificado>");

        tvIdentificacionDetalles.setText(flora.getIdentificacion());
        if(tvIdentificacionDetalles.getText().toString().isEmpty()) tvIdentificacionDetalles.setText("<No especificado>");

        tvAltitudDetalles.setText(flora.getAltitud());
        if(tvAltitudDetalles.getText().toString().isEmpty()) tvAltitudDetalles.setText("<No especificado>");

        tvHabitatDetalles.setText(flora.getHabitat());
        if(tvHabitatDetalles.getText().toString().isEmpty()) tvHabitatDetalles.setText("<No especificado>");

        tvFitosociologiaDetalles.setText(flora.getFitosociologia());
        if(tvFitosociologiaDetalles.getText().toString().isEmpty()) tvFitosociologiaDetalles.setText("<No especificado>");

        tvBiotipoDetalles.setText(flora.getBiotipo());
        if(tvBiotipoDetalles.getText().toString().isEmpty()) tvBiotipoDetalles.setText("<No especificado>");

        tvBiologiaReproductivaDetalles.setText(flora.getBiologia_reproductiva());
        if(tvBiologiaReproductivaDetalles.getText().toString().isEmpty()) tvBiologiaReproductivaDetalles.setText("<No especificado>");

        tvFloracionDetalles.setText(flora.getFloracion());
        if(tvFloracionDetalles.getText().toString().isEmpty()) tvFloracionDetalles.setText("<No especificado>");

        tvFructificacionDetalles.setText(flora.getFructificacion());
        if(tvFructificacionDetalles.getText().toString().isEmpty()) tvFructificacionDetalles.setText("<No especificado>");

        tvExpresionAsexualDetalles.setText(flora.getExpresion_sexual());
        if(tvExpresionAsexualDetalles.getText().toString().isEmpty()) tvExpresionAsexualDetalles.setText("<No especificado>");

        tvPolinizacionDetalles.setText(flora.getPolinizacion());
        if(tvPolinizacionDetalles.getText().toString().isEmpty()) tvPolinizacionDetalles.setText("<No especificado>");

        tvDispersionDetalles.setText(flora.getDispersion());
        if(tvDispersionDetalles.getText().toString().isEmpty()) tvDispersionDetalles.setText("<No especificado>");

        tvNumeroCromosomaticoDetalles.setText(flora.getNumero_cromosomatico());
        if(tvNumeroCromosomaticoDetalles.getText().toString().isEmpty()) tvNumeroCromosomaticoDetalles.setText("<No especificado>");

        tvReproduccionAsexualDetalles.setText(flora.getReproduccion_asexual());
        if(tvReproduccionAsexualDetalles.getText().toString().isEmpty()) tvReproduccionAsexualDetalles.setText("<No especificado>");

        tvDistribucionDetalles.setText(flora.getDistribucion());
        if(tvDistribucionDetalles.getText().toString().isEmpty()) tvDistribucionDetalles.setText("<No especificado>");

        tvBiologiaDetalles.setText(flora.getBiologia());
        if(tvBiologiaDetalles.getText().toString().isEmpty()) tvBiologiaDetalles.setText("<No especificado>");

        tvDemografiaDetalles.setText(flora.getDemografia());
        if(tvDemografiaDetalles.getText().toString().isEmpty()) tvDemografiaDetalles.setText("<No especificado>");

        tvAmenazasDetalles.setText(flora.getAmenazas());
        if(tvAmenazasDetalles.getText().toString().isEmpty()) tvAmenazasDetalles.setText("<No especificado>");

        tvMedidasPropuestasDetalles.setText(flora.getMedidas_propuestas());
        if(tvMedidasPropuestasDetalles.getText().toString().isEmpty()) tvMedidasPropuestasDetalles.setText("<No especificado>");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}