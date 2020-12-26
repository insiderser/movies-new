package com.insiderser.popularmovies.ui.reviews.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentAddReviewBinding
import com.insiderser.popularmovies.util.ReviewValidator
import com.insiderser.popularmovies.util.ValidationException
import com.insiderser.popularmovies.util.consume
import com.insiderser.popularmovies.util.observe
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import javax.inject.Inject

@AndroidEntryPoint
class AddReviewFragment : Fragment() {

    private var binding: FragmentAddReviewBinding by viewLifecycleScoped()

    private val viewModel: AddReviewViewModel by viewModels()

    private val navArgs: AddReviewFragmentArgs by navArgs()

    @Inject
    lateinit var reviewValidator: ReviewValidator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentAddReviewBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.applySystemWindowInsetsToPadding(top = true)
            root.applySystemWindowInsetsToPadding(bottom = true)

            authorInputEditText.doAfterTextChanged { validateAuthor() }
            contentInputEditText.doAfterTextChanged { validateContent() }

            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            toolbar.setOnMenuItemClickListener(::handleMenuClick)
        }

        viewModel.init(navArgs.movieId)
        viewModel.navigateBack.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun validateAuthor(): Boolean = with(binding) {
        authorInputLayout.error = null
        val author = authorInputEditText.text.toString()
        return try {
            reviewValidator.validateAuthor(author)
            true
        } catch (e: ValidationException) {
            when (e) {
                ValidationException.TooShortException -> authorInputLayout.error = getText(R.string.error_too_short)
            }
            false
        }
    }

    private fun validateContent(): Boolean = with(binding) {
        contentInputLayout.error = null
        val content = contentInputEditText.text.toString()
        return try {
            reviewValidator.validateContent(content)
            true
        } catch (e: ValidationException) {
            when (e) {
                ValidationException.TooShortException -> contentInputLayout.error = getText(R.string.error_too_short)
            }
            false
        }
    }

    @CheckResult
    private fun validate() = validateAuthor() && validateContent()

    private fun handleMenuClick(item: MenuItem): Boolean = when (item.itemId) {
        R.id.saveReview -> consume { save() }
        else -> false
    }

    private fun save() = with(binding) {
        if (!validate()) return

        val author = authorInputEditText.text.toString()
        val content = contentInputEditText.text.toString()

        viewModel.onCreateReviewClick(author, content)
    }
}
