package com.tmjee.myathena.ui.login

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentLoginBinding
import com.tmjee.myathena.databinding.FragmentLoginBindingImpl
import com.tmjee.myathena.domain.LoginResult
import com.tmjee.myathena.repository.PaymentsRepo
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject()
    lateinit var paymentsRepo: PaymentsRepo;  // field injection only if required

    lateinit var binding: FragmentLoginBinding

    val viewModel: LoginFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("********* login fragment onCreate() ${paymentsRepo}")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBarAndSetupWithNavController(
            toolbar=binding.appBarLayoutIncluded.toolbar,
            appBarConfiguration = appBarConfiguration,
            navController = navController,
            navigationView = null
        )

        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) {
            if(!it.handled) {
                when (it.type) {
                    LoginFragmentViewModel.EVT_TYPE_LOGIN_SUCCESS -> {
                        findNavController().navigate(LoginFragmentDirections.actionToPinValidation((it.payload as LoginResult).userId))
                    }
                    LoginFragmentViewModel.EVT_TYPE_FAILURE -> {
                        Toast.makeText(
                            requireContext(),
                            "Failure ${(it.payload as Throwable).message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    LoginFragmentViewModel.EVT_TYPE_USERNAME_VALIDATION_RESULT -> {
                        binding.username.error = if (it.payload as Boolean) {
                            null
                        } else {
                            "Username is invalid"
                        }
                    }
                    LoginFragmentViewModel.EVT_TYPE_PASSWORD_VALIDATION_RESULT -> {
                        binding.password.error = if (it.payload as Boolean) {
                            null
                        } else {
                            "Password is invalid"
                        }
                    }
                    LoginFragmentViewModel.EVT_TYPE_FORM_VALIDATION_RESULT -> {
                        binding.loginButton.isEnabled = (it.payload as Boolean)
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener() {
            viewModel.login()
        }
    }


}