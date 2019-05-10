package br.org.catolicasc.databasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Button btnAdd = findViewById(R.id.btnAdd);

        final int _id = getIntent().getIntExtra("_id", 0);

        if (_id == 0) {
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DAL dal = new DAL(InsertActivity.this);
                    EditText etTitle = findViewById(R.id.etTitle);
                    EditText etAuthor = findViewById(R.id.etAuthor);
                    EditText etPublisher = findViewById(R.id.etPublisher);

                    String title = etTitle.getText().toString();
                    String author = etAuthor.getText().toString();
                    String publisher = etPublisher.getText().toString();

                    if (dal.insert(title, author, publisher)) {
                        Toast.makeText(InsertActivity.this,
                                "Registro Inserido com sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(InsertActivity.this,
                                "Erro ao inserir registro!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            final DAL dal = new DAL(InsertActivity.this);

            final EditText etTitle = findViewById(R.id.etTitle);
            final EditText etAuthor = findViewById(R.id.etAuthor);
            final EditText etPublisher = findViewById(R.id.etPublisher);
            final TextView tvId = findViewById(R.id.lblAltera);
            Button btnExcluir = findViewById(R.id.btnExcluir);
            btnExcluir.setVisibility(View.VISIBLE);
            btnAdd.setText("Alterar");

            Cursor dbHelper = dal.findById(_id);

            final int idLivro = dbHelper.getInt(0);
            final String title = dbHelper.getString(1);
            final String author = dbHelper.getString(2);
            final String publisher = dbHelper.getString(3);

            etTitle.setText(title);
            etAuthor.setText(author);
            etPublisher.setText(publisher);
            tvId.setText("Você está alterando o livro com ID " + idLivro);
            tvId.setVisibility(View.VISIBLE);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dal.update(etTitle.getText().toString(), etAuthor.getText().toString(), etPublisher.getText().toString(), _id)) {
                        Toast.makeText(InsertActivity.this,
                                "Registro Alterado com sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(InsertActivity.this,
                                "Erro ao alterar registro!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dal.delete(_id)) {
                        Toast.makeText(InsertActivity.this,
                                "Registro Excluído com sucesso!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(InsertActivity.this,
                                "Erro ao excluir registro!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }
}
