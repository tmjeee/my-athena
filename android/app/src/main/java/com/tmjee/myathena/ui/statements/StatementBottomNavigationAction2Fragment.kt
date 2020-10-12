package com.tmjee.myathena.ui.statements

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.tmjee.myathena.databinding.FragmentStatementBottomNavigationAction2Binding
import com.tmjee.myathena.ui.setupWithNavController

class StatementBottomNavigationAction2Fragment: Fragment() {

    lateinit var bindings: FragmentStatementBottomNavigationAction2Binding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        bindings = FragmentStatementBottomNavigationAction2Binding.inflate(inflater, container, false)
        bindings.lifecycleOwner = this

        val navController = findNavController()

        val homeDrawerStatementFragment = parentFragment?.parentFragment as HomeDrawerStatementFragment

        setupWithNavController(homeDrawerStatementFragment.bindings.bottomNavigationView, navController)

        with(bindings.webview1) {
            settings.javaScriptEnabled = true
            webViewClient = object:WebViewClient() {
                override fun onReceivedError( view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    super.onReceivedError(view, request, error)
                    println("********* error ${error}")
                }

                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse? ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    println("********* http error ${request?.url} ${request?.method} ${errorResponse?.statusCode} ${errorResponse?.reasonPhrase} ")
                }

                override fun onReceivedSslError( view: WebView?, handler: SslErrorHandler?, error: SslError? ) {
                    super.onReceivedSslError(view, handler, error)
                    println("******** ssl error ${error}")
                }
            }
            webChromeClient = object:WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    bindings.progressBar.progress = newProgress
                    bindings.progressBar.visibility = if (newProgress < 100) VISIBLE else  GONE
                }
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                }
            }
        }
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindings.buttonReloadFromLink.setOnClickListener {
            bindings.webview1.loadUrl("http://fuyuko.org")
        }

        bindings.buttonReloadFromHtml.setOnClickListener {

            val html  = """
                <h1>Header</h1>
                <h3>Sub Header</h3>
                <p>
                    Some body text
                </p>     
            """.trimIndent()

            bindings.webview1.loadData(html, "text/html", "ascii")
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindings.webview1.loadUrl("http://www.google.com")
    }

}