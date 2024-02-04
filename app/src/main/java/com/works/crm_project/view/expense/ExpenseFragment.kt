package com.works.crm_project.view.expense


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.works.crm_project.R
import com.works.crm_project.adapter.customExpenseListAdapter
import com.works.crm_project.databinding.FragmentExpenseBinding
import com.works.crm_project.viewmodel.ExpenseViewModel
import kotlinx.coroutines.launch


class ExpenseFragment : Fragment() {

    private var _binding: FragmentExpenseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var controller: NavController
    private var viewModel: ExpenseViewModel? = null
    private var adapter: customExpenseListAdapter? = null
    private var listView: ListView? = null

    val profitValues = ArrayList<PieEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controller = Navigation.findNavController(view)
        listView = binding.listViewExpense

        lifecycleScope.launch {
            viewModel?.fetchAllExpense()
            viewModel?.fetchExpenseCategories()

            viewModel?.listAll?.observe(viewLifecycleOwner) {
                adapter = customExpenseListAdapter(requireContext(), R.layout.custom_expense_list, it)
                adapter?.makeListView(listView!!)
                listView?.adapter = adapter
            }

            profitValues.clear()
            dataListingAllExpenses()
        }

        super.onViewCreated(view, savedInstanceState)
    }


    fun dataListingAllExpenses() {
        viewModel?.listExpenseCategory?.observe(viewLifecycleOwner) { categories ->
            categories.forEach { category ->
                Log.e("deneme","deneme")
                calculateAllExpenseForCategory(category.id).observe(viewLifecycleOwner) { totalExpenses ->

                    totalExpenses.forEach { (description, amount) ->
                        Log.e("$description",amount.toString())
                        profitValues.add(PieEntry(amount.toFloat(), description))
                    }
                    setChart()
                }
            }
        }
    }

    fun setChart()
    {
        val pieDataSetter : PieDataSet

        val entries = mutableListOf<PieEntry>()

        profitValues.forEach { entry ->
            entries.add(entry)
        }

        // profitValues listesini güncelle
        profitValues.clear()
        profitValues.addAll(entries)

        if (binding.expenseChart.data != null && binding.expenseChart.data.dataSetCount > 0)
        {
            pieDataSetter = binding.expenseChart.data.getDataSetByIndex(0) as PieDataSet
            pieDataSetter.values = profitValues
            binding.expenseChart.data.notifyDataChanged()
            binding.expenseChart.notifyDataSetChanged()
        }
        else
        {
            pieDataSetter = PieDataSet(profitValues,"")

            val colors = intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.green),
                ContextCompat.getColor(requireContext(), R.color.orange),
                ContextCompat.getColor(requireContext(), R.color.red),
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.yellow),
                ContextCompat.getColor(requireContext(), R.color.pink)
            )

            pieDataSetter.setColors(*colors)
            pieDataSetter.setDrawValues(true)
            pieDataSetter.sliceSpace = 3f
            pieDataSetter.iconsOffset = MPPointF(10f,10f)
            pieDataSetter.selectionShift = 10f


            val dataSets = ArrayList<IPieDataSet>()
            dataSets.add(pieDataSetter)



            val data = PieData(pieDataSetter)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(10f)
            data.setValueTextColor(Color.GREEN)

            binding.expenseChart.data = data
            binding.expenseChart.invalidate()
            binding.expenseChart.description.isEnabled = false

            binding.expenseChart.animateY(1400,Easing.EaseInOutQuad)
            binding.expenseChart.setEntryLabelColor(Color.WHITE)

            binding.expenseChart.holeRadius = 13f

            binding.expenseChart.setTransparentCircleColor(R.color.black)
            binding.expenseChart.setTransparentCircleAlpha(110)
            binding.expenseChart.transparentCircleRadius = 17f

            binding.expenseChart.isDrawHoleEnabled = true

            binding.expenseChart.setUsePercentValues(true)

        }
    }

    fun calculateAllExpenseForCategory(id: Int): LiveData<List<Pair<String, Double>>> {
        val totalExpensesLiveData = MutableLiveData<List<Pair<String, Double>>>()

        viewModel?.listAll?.observe(viewLifecycleOwner) { expenses ->
            val totalExpensesMap = mutableMapOf<String, Double>()

            expenses.forEach { expense ->
                if (expense.expenseCategory.id == id) {
                    val categoryDescription = expense.expenseCategory.description
                    val currentTotal = totalExpensesMap.getOrDefault(categoryDescription, 0.0)
                    totalExpensesMap[categoryDescription] = currentTotal + expense.amount
                }
            }

            // Sonuçları Pair'ler halinde döndür
            val result = totalExpensesMap.map { entry ->
                Pair(entry.key, entry.value)
            }

            // LiveData'ya sonuçları gönder
            totalExpensesLiveData.value = result
        }

        return totalExpensesLiveData
    }

    fun calculateMyExpenseForCategory(id: Int): List<Pair<String, Double>> {
        val totalExpensesMap = mutableMapOf<String, Double>()

        viewModel?.listMy?.observe(viewLifecycleOwner) { expenses ->
            expenses.forEach { expense ->
                if (expense.expenseCategory.id == id) {
                    val categoryDescription = expense.expenseCategory.description
                    val currentTotal = totalExpensesMap.getOrDefault(categoryDescription, 0.0)
                    totalExpensesMap[categoryDescription] = currentTotal + expense.amount
                }
            }

            // Sonuçları Pair'ler halinde döndür
            val result = totalExpensesMap.map { entry ->
                Pair(entry.key, entry.value)
            }
            // Burada result'i kullanabilirsiniz.
        }

        // Observer çalışana kadar null döndür.
        return emptyList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}