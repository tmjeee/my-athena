package com.tmjee.myathena.ui.pinValidation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.tmjee.myathena.MainActivityViewModel
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.FragmentPinValidationBinding
import com.tmjee.myathena.domain.User
import com.tmjee.myathena.ui.setSupportActionBarAndSetupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinValidationFragment : Fragment() {

    lateinit var binding: FragmentPinValidationBinding
    val viewModel: PinValidationFragmentViewModel by viewModels()
    val activityViewModel: MainActivityViewModel by activityViewModels()
    val args: PinValidationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        binding = FragmentPinValidationBinding.inflate(inflater, container, false);
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.args = args

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBarAndSetupWithNavController(
            toolbar = binding.appBarLayoutIncluded.toolbar,
            navController = navController,
            appBarConfiguration = appBarConfiguration,
            navigationView = null
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.events.observe(viewLifecycleOwner) {
            if (!it.handled) {
                when (it.type) {
                    PinValidationFragmentViewModel.EVT_TYPE_REQUEST_PIN_SUCCESS -> {
                        Toast.makeText(
                            requireContext(),
                            "Request pin sucess, put in any pin",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    PinValidationFragmentViewModel.EVT_TYPE_REQUEST_PIN_FAILURE -> {
                        Toast.makeText(
                            requireContext(),
                            "Request pin failed ${(it.payload as Throwable).message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    PinValidationFragmentViewModel.EVT_TYPE_SUBMIT_PIN_SUCCESS -> {
                        val user: User = it.payload as User;
                        activityViewModel.user.value = user
                        findNavController().navigate(
                            PinValidationFragmentDirections.actionToHub(
                                user.userId
                            )
                        )
                    }
                    PinValidationFragmentViewModel.EVT_TYPE_SUBMIT_PIN_FAILURE -> {
                        Toast.makeText(
                            requireContext(),
                            "Submit pin failed ${(it.payload as Throwable).message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    PinValidationFragmentViewModel.EVT_TYPE_PIN_VALIDATION -> {
                        binding.smsValidation.error = if (it.payload as Boolean) {
                            null
                        } else {
                            "Invalid sms validation code"
                        }
                    }
                    PinValidationFragmentViewModel.EVT_TYPE_FORM_VALIDATION -> {
                        binding.buttonSubmitPin.isEnabled = it.payload as Boolean
                    }
                }
            }
        }

        binding.buttonRequestPin.setOnClickListener() {
            viewModel.requestPin()
        }

        binding.buttonSubmitPin.setOnClickListener() {
            viewModel.submitPin()
        }
    }
}