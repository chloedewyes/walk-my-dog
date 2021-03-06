package com.chloedewyes.walkmydog.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chloedewyes.walkmydog.R
import com.chloedewyes.walkmydog.databinding.FragmentCreateAccountBinding
import com.chloedewyes.walkmydog.db.User
import com.chloedewyes.walkmydog.ui.viewmodels.FirestoreViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccountFragment : Fragment(R.layout.fragment_create_account) {

    private var _binding: FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FirestoreViewModel by viewModels()

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.continueLayout.setOnClickListener {
            if (updateUI()) {
                createAccount(binding.etEmail.text.toString(), binding.etPassword.text.toString(), binding.etName.text.toString())
            } else {
                Snackbar.make(requireView(), "이메일과 비밀번호를 모두 입력해주세요 :)", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun createAccount(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {

                    val user = User(auth.uid, email, name)
                    viewModel.insertUser(user)

                    findNavController().navigate(R.id.trackingFragment)

                    Log.d("test", "createUserWithEmail:success")
                    Snackbar.make(requireView(), "회원가입에 성공했습니다 :)", Snackbar.LENGTH_SHORT).show()

                } else {
                    Log.w("test", "createUserWithEmail:failure", task.exception)
                    Snackbar.make(requireView(), "회원가입에 실패했습니다. 이메일과 비밀번호를 확인해주세요 :(", Snackbar.LENGTH_SHORT).show()
                }
            }
    }


    private fun updateUI(): Boolean {
        val email = binding.etEmail.text.toString()
        val weight = binding.etPassword.text.toString()
        val name = binding.etName.text.toString()
        if (email.isEmpty() || weight.isEmpty() || name.isEmpty()) {
            return false
        }
        return true
    }

}