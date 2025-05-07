package com.example.thiagoseridonio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var editTextCep: EditText
    private lateinit var buttonBuscar: Button
    private lateinit var buttonSave: Button
    private lateinit var textViewResultado: TextView

    private lateinit var cepViewModel: CepViewModel
    private lateinit var cepDatabase: AppDatabase
    private lateinit var repository: CepRepository

    private var lastCepResponse: ViaCepResponse? = null // Para salvar o último resultado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        setupRetrofitLogic()
        setupRoomLogic()
    }

    // Primeiro bloco: lógica de busca de CEP com Retrofit
    private fun setupRetrofitLogic() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ViaCepService::class.java)

        buttonBuscar.setOnClickListener {
            val cepInput = editTextCep.text.toString().trim()

            if (cepInput.isNotEmpty()) {
                service.buscarCep(cepInput).enqueue(object : Callback<ViaCepResponse> {
                    override fun onResponse(call: Call<ViaCepResponse>, response: Response<ViaCepResponse>) {
                        if (response.isSuccessful) {
                            val cepInfo = response.body()
                            lastCepResponse = cepInfo
                            textViewResultado.text = getString(
                                R.string.cep_resultado,
                                cepInfo?.cep ?: "",
                                cepInfo?.logradouro ?: "",
                                cepInfo?.bairro ?: "",
                                cepInfo?.localidade ?: "",
                                cepInfo?.uf ?: ""
                            )
                        } else {
                            textViewResultado.text = getString(R.string.cep_nao_encontrado)
                        }
                    }

                    override fun onFailure(call: Call<ViaCepResponse>, t: Throwable) {
                        textViewResultado.text = getString(R.string.erro_cep, t.message ?: "Erro desconhecido")
                    }
                })
            } else {
                textViewResultado.text = getString(R.string.mensagem_digite_cep)
            }
        }
    }

    // Segundo bloco: lógica de salvar no banco com ViewModel + Room
    private fun setupRoomLogic() {
        cepDatabase = AppDatabase.getDatabase(applicationContext)
        repository = CepRepository(cepDatabase.cepDao())
        cepViewModel = ViewModelProvider(this, CepViewModelFactory(repository)).get(CepViewModel::class.java)

        buttonSave.setOnClickListener {
            lastCepResponse?.let {
                val cep = Cep(
                    cep = it.cep ?: "",
                    logradouro = it.logradouro ?: "",
                    bairro = it.bairro ?: "",
                    cidade = it.localidade ?: "",
                    uf = it.uf ?: ""
                )
                cepViewModel.insert(cep)
            }
        }
    }

    // Inicialização de elementos de interface
    private fun initUI() {
        editTextCep = findViewById(R.id.editTextCep)
        buttonBuscar = findViewById(R.id.buttonBuscar)
        buttonSave = findViewById(R.id.buttonSave)
        textViewResultado = findViewById(R.id.textViewResultado)
    }
}
