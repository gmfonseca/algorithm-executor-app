package br.com.gmfonseca.gamma.algorithmexecutorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.gmfonseca.gamma.algorithmexecutorapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        return
    }
}
