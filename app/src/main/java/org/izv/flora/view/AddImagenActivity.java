package org.izv.flora.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.Imagen;
import org.izv.flora.viewmodel.AddFloraViewModel;
import org.izv.flora.viewmodel.AddImagenViewModel;
import org.izv.flora.viewmodel.MainActivityViewModel;

public class AddImagenActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private Intent resultadoImagen = null;
    private EditText etNombre, etDescripcion;
    private AddImagenViewModel aivm;
    private Spinner spFlora;
    private Button btVolverAddImagen;
    private ImageView ivFotoAddImagen;
    private Flora flora;
    private Imagen imagen;
    private Boolean fromFlora = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imagen);

        Bundle b = getIntent().getExtras();
        setComponents();
        if(b != null) {
            fromFlora=true;
            flora = b.getParcelable("flora");
            imagen.setIdflora(flora.getId());
            spFlora.setVisibility(View.GONE);
        }

        initialize();
    }

    private void setComponents() {
        imagen = new Imagen();
        launcher = getLauncher();

        etDescripcion = findViewById(R.id.etDescripcion);
        etNombre = findViewById(R.id.etNombreImagen);
        spFlora = findViewById(R.id.spFlora);
        btVolverAddImagen = findViewById(R.id.btVolverAddImagen);
        ivFotoAddImagen = findViewById(R.id.ivFotoAddImagen);


        Glide.with(getApplicationContext()).load(R.drawable.upload_image).into(ivFotoAddImagen);

        aivm = new ViewModelProvider(this).get(AddImagenViewModel.class);

        desplegable();
    }

    private void initialize() {
        Button btAddImagen = findViewById(R.id.btAddImagen);
        btAddImagen.setOnClickListener(v -> {
            uploadDataImage();
        });

        ivFotoAddImagen.setOnClickListener(view -> selectImage());
        btVolverAddImagen.setOnClickListener(view -> finish());
    }

    private void desplegable() {
        //Cargar el spinner
        aivm.getFlora();
        aivm.getFloraLiveData().observe(this, floras -> {
            //Creo el por defecto
            Flora floraAux = new Flora();
            floraAux.setNombre("Seleccione Flora");
            floraAux.setId(0);
            //Lo añado al array con el resto de floras
            floras.add(0, floraAux);
            //Creo el array adapter del spinner
            ArrayAdapter<Flora> adapter =
                    new ArrayAdapter<Flora>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, floras);
            //Le asigno el array adapter al spinner
            spFlora.setAdapter(adapter);
        });
    }

    private void uploadDataImage() {
        Flora flora = (Flora) spFlora.getSelectedItem();

        String nombre = etNombre.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        Long idFlora = flora.getId();
        //Comporbamos los campos
        if(!(nombre.trim().isEmpty() || idFlora < 0 || resultadoImagen == null)) {
            imagen.nombre = nombre;
            imagen.descripcion = descripcion;
            if(!fromFlora) {
                imagen.idflora = idFlora;
            }
            aivm.saveImagen(resultadoImagen, imagen);
            Toast.makeText(AddImagenActivity.this, "Imagen subida", Toast.LENGTH_LONG).show();

            //Salimos
            finish();
        }else{
            Toast.makeText(AddImagenActivity.this, "No se ha podido subir la imagen, compruebe los campos(el campo nombre debe de ser único).", Toast.LENGTH_LONG).show();
        }
    }

    ActivityResultLauncher<Intent> getLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //respuesta al resultado de haber seleccionado una imagen
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        //copyData(result.getData());
                        resultadoImagen = result.getData();
                        Glide.with(getApplicationContext()).load(resultadoImagen.getData().toString()).into(ivFotoAddImagen);

                    }
                }
        );
    }

    Intent getContentIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    void selectImage() {
        Intent intent = getContentIntent();
        launcher.launch(intent);
    }
}