package org.izv.flora.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.viewmodel.AddFloraViewModel;

public class AddFloraActivity extends AppCompatActivity {

    private ConstraintLayout layoutCreacionFlora;
    private TextInputLayout tilNombre;
    private TextInputEditText etNombre, etFamilia, etIdentificacion, etAltitud, etHabitat, etFitosociologia, etBiotipo, etBiologiaReproductiva, etFloracion, etFructificacion, etExpresionSexual, etPolinizacion, etDispersion, etNumeroCromosomatico, etReproduccionAsexual, etDistribucion, etBiologia, etDemografia, etAmenazas, etMedidasPropuestas;
    private Button btAdd, btCancel;
    private Flora flora;
    private AddFloraViewModel afvm;
    private TextView tvTituloCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flora);
        setComponents();
        Bundle b = getIntent().getExtras();
        setComponents();
        btCancel.setOnClickListener(view -> finish());
        if(b != null) {
            flora = b.getParcelable("flora");
            initializeEdit();
        }else {
            initialize();
        }
    }

    private void initialize() {
        afvm.getAddFloraLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if(aLong > 0) {
                    Toast.makeText(AddFloraActivity.this, "\""+etNombre.getText().toString().trim()+"\" creada", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AddFloraActivity.this, "Error, no se ha podido guardar en la Base de Datos", Toast.LENGTH_LONG).show();
                }
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Flora nombre obligatoria
                if(etNombre.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddFloraActivity.this, "Complete el Nombre", Toast.LENGTH_LONG).show();
                    tilNombre.setError("Campo obligatorio");
                    return;
                }

                //Creamos la flora que guardaremos
                Flora floraNew = creamosFlora();

                //Lo mandamos al AddFloraViewModel
                afvm.createFlora(floraNew);
            }
        });
    }

    private Flora creamosFlora() {
        //Creamos la flora que devolveremos
        Flora flora2 = new Flora();

        //Setteamos la flora
        flora2.setNombre(etNombre.getText().toString().trim());
        flora2.setFamilia(etFamilia.getText().toString().trim());
        flora2.setIdentificacion(etIdentificacion.getText().toString().trim());
        flora2.setAltitud(etAltitud.getText().toString().trim());
        flora2.setHabitat(etHabitat.getText().toString().trim());
        flora2.setFitosociologia(etFitosociologia.getText().toString().trim());
        flora2.setBiotipo(etBiotipo.getText().toString().trim());
        flora2.setBiologia_reproductiva(etBiologiaReproductiva.getText().toString().trim());
        flora2.setFloracion(etFloracion.getText().toString().trim());
        flora2.setFructificacion(etFructificacion.getText().toString().trim());
        flora2.setExpresion_sexual(etExpresionSexual.getText().toString().trim());
        flora2.setPolinizacion(etPolinizacion.getText().toString().trim());
        flora2.setDispersion(etDispersion.getText().toString().trim());
        flora2.setNumero_cromosomatico(etNumeroCromosomatico.getText().toString().trim());
        flora2.setReproduccion_asexual(etReproduccionAsexual.getText().toString().trim());
        flora2.setDistribucion(etDistribucion.getText().toString().trim());
        flora2.setBiologia(etBiologia.getText().toString().trim());
        flora2.setDemografia(etDemografia.getText().toString().trim());
        flora2.setAmenazas(etAmenazas.getText().toString().trim());
        flora2.setMedidas_propuestas(etMedidasPropuestas.getText().toString().trim());

        //La devolvemos
        return flora2;
    }

    private void setComponents() {
        tvTituloCrear = findViewById(R.id.tvTituloCrear);

        //Setteamos el layout para la animcion
        layoutCreacionFlora = findViewById(R.id.layoutCreacionFlora);

        //Setteamos los TextInputLayout
        tilNombre = findViewById(R.id.tilNombre);

        //Setteamos los TextInputEditText
        etNombre = findViewById(R.id.etNombre);
        etFamilia = findViewById(R.id.etFamilia);
        etIdentificacion = findViewById(R.id.etIdentificacion);
        etAltitud = findViewById(R.id.etAltitud);
        etHabitat = findViewById(R.id.etHabitat);
        etFitosociologia = findViewById(R.id.etFitosociologia);
        etBiotipo = findViewById(R.id.etBiotipo);
        etBiologiaReproductiva = findViewById(R.id.etBiologiaReproductiva);
        etFloracion = findViewById(R.id.etFloracion);
        etFructificacion = findViewById(R.id.etFructificacion);
        etExpresionSexual = findViewById(R.id.etExpresionSexual);
        etPolinizacion = findViewById(R.id.etPolinizacion);
        etDispersion = findViewById(R.id.etDispersion);
        etNumeroCromosomatico = findViewById(R.id.etNumeroCromosomatico);
        etReproduccionAsexual = findViewById(R.id.etReproduccionAsexual);
        etDistribucion = findViewById(R.id.etDistribucion);
        etBiologia = findViewById(R.id.etBiologia);
        etDemografia = findViewById(R.id.etDemografia);
        etAmenazas = findViewById(R.id.etAmenazas);
        etMedidasPropuestas = findViewById(R.id.etMedidasPropuestas);

        //Setteamos los Buttons
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);

        afvm = new ViewModelProvider(this).get(AddFloraViewModel.class);
    }

    private void initializeEdit() {
        fillFields();
        btAdd.setText("Editar");
        tvTituloCrear.setText("Editar Flora");
        btAdd.setOnClickListener(view -> {
            //Flora nombre obligatoria
            if(etNombre.getText().toString().trim().isEmpty()){
                Toast.makeText(AddFloraActivity.this, "Complete el Nombre", Toast.LENGTH_LONG).show();
                tilNombre.setError("Campo obligatorio");
                return;
            }

            //Creamos la flora que guardaremos
            Flora floraNew = creamosFlora();

            //Lo mandamos al AddFloraViewModel
            afvm.editFlora(flora.getId(), floraNew);

            finish();
        });
    }

    private void fillFields() {
        etNombre.setText(flora.getNombre());

        etFamilia.setText(flora.getFamilia());


        etIdentificacion.setText(flora.getIdentificacion());


        etAltitud.setText(flora.getAltitud());


        etHabitat.setText(flora.getHabitat());


        etFitosociologia.setText(flora.getFitosociologia());


        etBiotipo.setText(flora.getBiotipo());


        etBiologiaReproductiva.setText(flora.getBiologia_reproductiva());


        etFloracion.setText(flora.getFloracion());


        etFructificacion.setText(flora.getFructificacion());


        etExpresionSexual.setText(flora.getExpresion_sexual());


        etPolinizacion.setText(flora.getPolinizacion());


        etDispersion.setText(flora.getDispersion());


        etNumeroCromosomatico.setText(flora.getNumero_cromosomatico());


        etReproduccionAsexual.setText(flora.getReproduccion_asexual());


        etDistribucion.setText(flora.getDistribucion());


        etBiologia.setText(flora.getBiologia());


        etDemografia.setText(flora.getDemografia());


        etAmenazas.setText(flora.getAmenazas());


        etMedidasPropuestas.setText(flora.getMedidas_propuestas());

    }
}