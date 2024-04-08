package com.yagubbilalzade.testtranslation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.yagubbilalzade.testtranslation.databinding.ActivityMainBinding
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val translateButton = binding.btnTranslate
        val etUser = binding.etText
        val tvTranslation = binding.tvTranslation

        var downloaded = false


        val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.RUSSIAN).build()
        val englishRussianTranslator = Translation.getClient(options)


        var conditions = DownloadConditions.Builder().requireWifi().build()
        englishRussianTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {

                downloaded = true
            }.addOnFailureListener { exception ->
                downloaded = false
            }


        translateButton.setOnClickListener {
            if (downloaded) {
                val inputText = etUser.text.toString()
                englishRussianTranslator.translate(inputText)
                    .addOnSuccessListener { translatedText ->

                        tvTranslation.text = translatedText
                    }.addOnFailureListener { exception ->
                        tvTranslation.text = "Failed translateing ..."
                    }
            } else {

                tvTranslation.text = "downloading ..."


                englishRussianTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener {


                        tvTranslation.text = "downloaded!!!!"
                    }.addOnFailureListener { exception ->
                        tvTranslation.text = "Failed downloading ..."
                    }
            }
        }


    }
}

