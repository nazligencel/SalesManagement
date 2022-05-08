/*
 * Created by JFormDesigner on Thu Apr 14 00:14:39 TRT 2022
 */

package views;

import java.beans.*;
import javax.swing.event.*;
import com.github.lgooddatepicker.components.DatePicker;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import models.*;
import props.*;

import utils.Util;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;


import java.util.List;

/**
 * @author unknown
 */
public class Dashboard extends Base {

    CustomersImpl customersImpl = new CustomersImpl();
    CategoriesImpl categoriesImpl = new CategoriesImpl();
    BasketImpl basketImpl = new BasketImpl();
    SalesImpl salesImpl = new SalesImpl();
    ProductsImpl productsImpl = new ProductsImpl();
    UserImpl userImpl=new UserImpl();



    int status = 0;
    int row = -1;
    int selectedId = 0;
    int categoryId_SelectedProduct =0;
    int categoryId_SelectedProductUpdate =0;
    int categoryId_SelectedBasket = 0;
    String date1 = "1900-12-01";
    String date2 = "2500-12-01";
    SalesImpl s = new SalesImpl(date1, date2);
    Categories categoryProduct_Added;

    public Dashboard() {
        initComponents();
        tblCustomer.setModel(customersImpl.tablemodelCreate());
        tblCategory.setModel(categoriesImpl.categoryTable());
        tblProduct.setModel(productsImpl.productTableModel());
        cmbPrpductAdd.setModel(categoriesImpl.cmbmodel());
        cmbProductUpdate.setModel(categoriesImpl.cmbmodel());
        tblCustomerSale.setModel(basketImpl.customerTableModelBasket(""));
        cmbcategory2.setEnabled(false);
        tblproductSale.setEnabled(false);
        spinnerquantity.setEnabled(false);
        txtSearchProduct3.setEditable(false);
        txtDateSale.setEditable(false);
        String date1 = "1900-12-01";
        String date2 = "2500-12-01";
        String totalProfit=String.valueOf(salesImpl.CalculateTotalProfit(salesImpl.baketReportList(date1,date2)));
        lblRepotsProfit.setText(totalProfit);


    }

    private void btnNewCustomerAdd(ActionEvent e) {
        Customer c = fncDataValidate_CustomerAdd();
        if (c != null) {
            int statusCustomerAdd = customersImpl.customerInsert(c);
            if (statusCustomerAdd > 0) {
                lblCustomerError.setText("");
                txtCustomerAddName.setText("");
                txtCustomerAddSurname.setText("");
                txtCustomerAddEmail.setText("");
                txtCustomerAddPhone.setText("");
                txtCustomerAddAddress.setText("");
                tblCustomer.setModel(customersImpl.tablemodelCreate());
                BasketImpl basketImpl = new BasketImpl();
                tblCustomerSale.setModel(basketImpl.customerTableModelBasket(null));
                tblCustomerSale.removeColumn(tblCustomerSale.getColumnModel().getColumn(0));
            } else {
                if (statusCustomerAdd == -1) {
                    lblCustomerError.setText("Email or phone have already used.");
                } else {
                    lblCustomerError.setText("Unknown Insert Error Please try after. ");
                }
            }
        }
    }

    public Customer fncDataValidate_CustomerAdd() {

        String customerSurname = txtCustomerAddSurname.getText().trim();
        String customerName = txtCustomerAddName.getText().trim();
        String customerEmail = txtCustomerAddEmail.getText().trim().toLowerCase(Locale.ROOT);
        String customerPhone = txtCustomerAddPhone.getText().trim();
        String customerAddress = txtCustomerAddAddress.getText().trim();

        if (customerName.equals("")) {
            lblCustomerError.setText("Please enter customer name.");
            txtCustomerAddName.requestFocus();
        } else if (customerSurname.equals("")) {
            lblCustomerError.setText("Please enter customer surname.");
            txtCustomerAddSurname.requestFocus();
        } else if (customerPhone.equals("")) {
            lblCustomerError.setText("Please enter customer phone.");
            txtCustomerAddPhone.requestFocus();
        } else if (customerEmail.equals("")) {
            lblCustomerError.setText("Please enter e-mail address.");
            txtCustomerAddEmail.requestFocus();
        } else if (!Util.isValidEmailAddress(customerEmail)) {
            lblCustomerError.setText(" Format of e-mail address is entered is wrong. ");
        } else if (customerAddress.equals("")) {
            lblCustomerError.setText("Please enter customer address.");
            txtCustomerAddAddress.requestFocus();
        } else {
            String customerNameUpped = customerName.substring(0, 1).toUpperCase(Locale.ROOT) +
                    customerName.substring(1).toLowerCase(Locale.ROOT);
            String customerSurnameUpped = customerSurname.substring(0, 1).toUpperCase(Locale.ROOT) +
                    customerSurname.substring(1).toLowerCase(Locale.ROOT);

            return new Customer(0, customerNameUpped, customerSurnameUpped, customerEmail, customerPhone, customerAddress);
        }
        return null;
    }

    public Customer fncDataValidate_CustomerEdit() {
        int row = tblCustomer.getSelectedRow();
        String customerSurname = txtCustomerEditSurname.getText().trim();
        String customerName = txtCustomerEditName.getText().trim();
        String customerEmail = txtCustomerEditEmail.getText().trim().toLowerCase(Locale.ROOT);
        String customerPhone = txtCustomerEditPhone.getText().trim();
        String customerAddress = txtCustomerEditAdress.getText().trim();
        if (row == -1) {
            lblCustomerError2.setText("Please select a customer that you want update");
        } else if (customerName.equals("")) {
            lblCustomerError2.setText("Please enter customer name.");
        } else if (customerSurname.equals("")) {
            lblCustomerError2.setText("Please enter customer surname.");
        } else if (customerPhone.equals("")) {
            lblCustomerError2.setText("Please enter customer phone.");
        } else if (customerEmail.equals("")) {
            lblCustomerError2.setText("Please enter e-mail address.");
        } else if (!Util.isValidEmailAddress(customerEmail)) {
            lblCustomerError2.setText(" Format of e-mail address is entered is wrong. ");
        } else if (customerAddress.equals("")) {
            lblCustomerError2.setText("Please enter customer address.");
        } else {
            int selectedId = (Integer) tblCustomer.getValueAt(row, 0);
            String customerNameUpped = customerName.substring(0, 1).toUpperCase(Locale.ROOT) +
                    customerName.substring(1).toLowerCase(Locale.ROOT);
            String customerSurnameUpped = customerSurname.substring(0, 1).toUpperCase(Locale.ROOT) +
                    customerSurname.substring(1).toLowerCase(Locale.ROOT);
            Customer customer = new Customer(selectedId, customerNameUpped, customerSurnameUpped, customerEmail, customerPhone, customerAddress);
            return customer;
        }
        return null;
    }

    private void tblCustomerMouseReleased(MouseEvent e) {
        int row = tblCustomer.getSelectedRow();
        String name = (String) tblCustomer.getValueAt(row, 1);
        String surname = (String) tblCustomer.getValueAt(row, 2);
        String email = (String) tblCustomer.getValueAt(row, 3);
        String phone = (String) tblCustomer.getValueAt(row, 4);
        String address = (String) tblCustomer.getValueAt(row, 5);

        txtCustomerEditName.setText(name);
        txtCustomerEditSurname.setText(surname);
        txtCustomerEditEmail.setText(email);
        txtCustomerEditPhone.setText(phone);
        txtCustomerEditAdress.setText(address);

    }

    private void btnCustomerUpdate(ActionEvent e) {
        Customer c = fncDataValidate_CustomerEdit();
        if (c != null) {
            int status = customersImpl.customerUpdate(c);
            if (status > 0) {
                lblCustomerError2.setText("");
                txtCustomerEditName.setText("");
                txtCustomerEditSurname.setText("");
                txtCustomerEditEmail.setText("");
                txtCustomerEditPhone.setText("");
                txtCustomerEditAdress.setText("");
                tblCustomer.setModel(customersImpl.tablemodelCreate());
                BasketImpl basketImpl = new BasketImpl();
                tblCustomerSale.setModel(basketImpl.customerTableModelBasket(null));
            } else {
                if (status == -1) {
                    lblCustomerError2.setText("Email or phone have already used.");
                } else {
                    lblCustomerError2.setText("Unknown Update Error Please try after. ");
                }
            }

        }

    }

    private void btnCustomerDelete(ActionEvent e) {
        int row = tblCustomer.getSelectedRow();

        if (row != -1) {
            int selectedId = (Integer) tblCustomer.getValueAt(row, 0);
            if(salesImpl.isContainCustomer(selectedId)){
                JOptionPane.showMessageDialog(this,"This customer not deleted because customer have a sale ",
                        "Warning!",  JOptionPane.WARNING_MESSAGE);

            }else{
            int answer = JOptionPane.showConfirmDialog(this,
                    "Are you sure that you want to customer."
                    , "Delete Process", JOptionPane.YES_NO_OPTION);
            if (answer == 0) {

                int status = customersImpl.customerDelete(selectedId);
                if (status > 0) {
                    lblCustomerError2.setText("");
                    txtCustomerEditName.setText("");
                    txtCustomerEditSurname.setText("");
                    txtCustomerEditEmail.setText("");
                    txtCustomerEditPhone.setText("");
                    txtCustomerEditAdress.setText("");
                    tblCustomer.setModel(customersImpl.tablemodelCreate());
                    tblCustomer.clearSelection();
                    BasketImpl basketImpl = new BasketImpl();
                    tblCustomerSale.setModel(basketImpl.customerTableModelBasket(null));

                }
            }}
        } else {
            lblCustomerError2.setText("Please select a customer that you want update");
        }
    }

    private Categories fncDataValidCategories() {
        String categoryName = txtCategoryName.getText().trim();
        String categoryDescription = txtCategoryDescription.getText().trim();

        if (categoryName.equals("")) {
            lblError.setText("Category Name Empty!!");
            txtCategoryName.requestFocus();
        } else if (categoryDescription.equals("")) {
            lblError.setText("Category Description Empty!!");
            txtCategoryDescription.requestFocus();
        } else {
            lblError.setText("");
            Categories categories = new Categories(selectedId, categoryName, categoryDescription);
            return categories;
        }
        return null;
    }

    public void textboxClear() {
        txtCategoryName.setText("");
        txtCategoryDescription.setText("");

    }


    private void tblCategoryMouseReleased(MouseEvent e) {
        row = tblCategory.getSelectedRow();

        selectedId = (Integer) tblCategory.getValueAt(row, 0);
        String categoryName = (String) tblCategory.getValueAt(row, 1);
        String categoryDescription = (String) tblCategory.getValueAt(row, 2);

        txtCategoryName.setText(categoryName);
        txtCategoryDescription.setText(categoryDescription);
    }

    private void txtCategoryNameKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            fncDataValidCategories();
        }
    }

    private void txtCategoryDescriptionKeyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            fncDataValidCategories();
        }
    }

    private void btnCategoryAdd(ActionEvent e) {
        Categories categories = fncDataValidCategories();
        if (categories != null) {
            int status = categoriesImpl.categoryInsert(categories);
            if (status > 0) {
                System.out.println("Insert Success");
            } else {
                if (status == -1) {
                    lblError.setText("This category has already been added");
                } else {
                    lblError.setText("Insert Error");

                }

            }
            tblCategory.clearSelection();
            textboxClear();
            String categoryName = txtCategoryName.getText();
            String categoryDescription = txtCategoryDescription.getText();

            Categories cat = new Categories(0, categoryName, categoryDescription);
            tblCategory.setModel(categoriesImpl.categoryTable());
            cmbPrpductAdd.setModel(categoriesImpl.cmbmodel());
            cmbProductUpdate.setModel(categoriesImpl.cmbmodel());
            cmbcategory2.setModel(categoriesImpl.cmbmodel());

        }
    }

    private void btnCategoryDelete(ActionEvent e) {
        row = tblCategory.getSelectedRow();
        System.out.println(selectedId);
        System.out.println(row);
        if (row == -1) {

            lblError.setText("Please select the category you want to delete from the table");

        }
        else {

            if (salesImpl.isContainCategories(selectedId)){
                JOptionPane.showMessageDialog(this,"This Categories not deleted because Basket have a sale ",
                        "Warning!",  JOptionPane.WARNING_MESSAGE);
            }
            else{
            lblError.setText("");
            int answer = JOptionPane.showConfirmDialog(this, "Are you sure that you want to delete the category");
            if (answer == 0) {
                categoriesImpl.categoryDelete(selectedId);
                tblCategory.setModel(categoriesImpl.categoryTable());
                cmbPrpductAdd.setModel(categoriesImpl.cmbmodel());
                cmbProductUpdate.setModel(categoriesImpl.cmbmodel());
                cmbcategory2.setModel(categoriesImpl.cmbmodel());

            }
            tblCategory.clearSelection();
            textboxClear();

            }
        }

    }

    private void btnCategoryUpdate(ActionEvent e) {
        Categories categories = fncDataValidCategories();
        row = tblCategory.getSelectedRow();
        if (row == -1) {
            lblError.setText("Please select the category you want to update from the table");

        } else {
            lblError.setText("");
            int answer = JOptionPane.showConfirmDialog(this, "Do you want to change!");
            if (answer == 0) {

                if (categories != null) {
                    status = categoriesImpl.categoryUpdate(categories);
                }
                if (status > 0) {
                    textboxClear();
                    tblCategory.setModel(categoriesImpl.categoryTable());
                    cmbPrpductAdd.setModel(categoriesImpl.cmbmodel());
                    cmbProductUpdate.setModel(categoriesImpl.cmbmodel());
                    cmbcategory2.setModel(categoriesImpl.cmbmodel());

                }
            } else {
                tblCategory.clearSelection();
                textboxClear();
            }
        }
    }

    public static boolean isDouble(String d){
        boolean valid = true;

        if(d.matches("^[0-9]+([,.][0-9]?)?$") || d.length() < 1 || d.length() > 9)
        {
            valid = false;
        }

        return valid;
    }
    public static boolean isInt (String s){
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private Products fncDataValidProductAdd(){

        if(txtProductAddName.getText().equals("")){
            lbllAddError.setText("Products Name Empty !!");
            txtProductAddName.requestFocus();
        }else if(cmbPrpductAdd.getSelectedIndex()==-1){
            lbllAddError.setText("Category Empty!");
            cmbPrpductAdd.requestFocus();
        }
        else if(txtProductAddPurcPrice.getText().equals("")){
            lbllAddError.setText("Purchase Price is empty");
            txtProductAddPurcPrice.requestFocus();
        }else if(txtProductAddStockQua.getText().equals("")){
            lbllAddError.setText("Stock Quantity Empty!");
            txtProductUpdateStockQua.requestFocus();
        }else if(txtProductAddSalePrice.getText().equals("")){
            lbllAddError.setText("Sale Price Empty!");
            txtProductAddSalePrice.requestFocus();
        }else if (isDouble(txtProductAddPurcPrice.getText())){
            lbllAddError.setText("Purchase price not Double!");
            txtProductAddPurcPrice.requestFocus();
        }else if(!isInt(txtProductAddStockQua.getText())){
            lbllAddError.setText("Stock Quantity is not Number!");
            txtProductAddStockQua.requestFocus();
        }
        else if (isDouble(txtProductAddSalePrice.getText())){
            lbllAddError.setText("Sale price not Double!");
            txtProductAddSalePrice.requestFocus();
        }else {
            lbllAddError.setText("");
            String productname= txtProductAddName.getText().trim();
            double purchaseprice = Double.parseDouble(txtProductAddPurcPrice.getText().trim());
            int stockquantity= Integer.parseInt(txtProductAddStockQua.getText().trim());
            double saleprice= Double.parseDouble(txtProductAddSalePrice.getText().trim());
            lbllAddError.setText("");
            Categories c = (Categories) cmbPrpductAdd.getSelectedItem();
            categoryId_SelectedProduct = c.getCatid();
            String categoryName=c.getCatecoryname();
            String catDescription=c.getCategoryDescription();
            categoryProduct_Added=new Categories(categoryId_SelectedProduct,categoryName,catDescription);
            Products products = new Products(0, categoryId_SelectedProduct,productname,purchaseprice,stockquantity,saleprice);
            return products;
        }return null;
    }

    private Products fncDataValidProductUPD(){
        String productname= txtProductUpdateName.getText().trim();


        if(productname.equals("")){
            lblUpdateError.setText("Products Name Empty !!");
            txtProductAddName.requestFocus();
        }else if(cmbProductUpdate.getSelectedIndex()==-1){
            lblUpdateError.setText("Category Empty!");
            cmbProductUpdate.requestFocus();
        }else if(txtProductUpdatePurcPrice.getText().trim().equals("")){
            lblUpdateError.setText("Purchase Price is empty");
            txtProductUpdatePurcPrice.requestFocus();
        }else if(txtProductUpdateStockQua.getText().trim().equals("")){
            lblUpdateError.setText("Stock Quantity Empty!");
            txtProductUpdateStockQua.requestFocus();
        }else if(txtProductUpdateSalePrice.getText().trim().equals("")){
            lblUpdateError.setText("Sale Price Empty!");
            txtProductUpdateSalePrice.requestFocus();
        }else if (isDouble(txtProductUpdatePurcPrice.getText())){
            lblUpdateError.setText("Purchase price not Double!");
            txtProductAddPurcPrice.requestFocus();
        }else if(!isInt(txtProductUpdateStockQua.getText())){
            lblUpdateError.setText("Stock Quantity is not Number!");
            txtProductUpdateStockQua.requestFocus();
        }
        else if (isDouble(txtProductUpdateSalePrice.getText())){
            lblUpdateError.setText("Sale price not Double!");
            txtProductUpdateSalePrice.requestFocus();
        }else {
            lblUpdateError.setText("");
            double purchaseprice = Double.parseDouble(txtProductUpdatePurcPrice.getText().trim());
            int stockquantity= Integer.parseInt(txtProductUpdateStockQua.getText().trim());
            double saleprice= Double.parseDouble(txtProductUpdateSalePrice.getText().trim());
            int row=tblProduct.getSelectedRow();
            Categories c = (Categories) cmbProductUpdate.getSelectedItem();
            categoryId_SelectedProductUpdate = c.getCatid();
            String categoryName=c.getCatecoryname();
            String catDescription=c.getCategoryDescription();
            categoryProduct_Added=new Categories(categoryId_SelectedProductUpdate,categoryName,catDescription);
            int id=(Integer) tblProduct.getValueAt(row,0);
            Products products = new Products(id, categoryId_SelectedProductUpdate,productname,purchaseprice,stockquantity,saleprice);
            return products;

        }
        return null;
    }
    public void textboxProdAddClear(){
        txtProductAddName.setText("");
        txtProductAddSalePrice.setText("");
        txtProductAddStockQua.setText("");
        txtProductAddPurcPrice.setText("");


    }

    public void textboxProdUPDClear(){
        lblUpdateError.setText("");
        txtProductUpdateName.setText("");
        txtProductUpdateSalePrice.setText("");
        txtProductUpdateStockQua.setText("");
        txtProductUpdatePurcPrice.setText("");

    }


    private void btnProductAdd(ActionEvent e) {

        Products products=fncDataValidProductAdd();
        if(products != null){
            status=productsImpl.productInsert(products);
        }if(status>0){

            textboxProdAddClear();
            tblProduct.setModel(productsImpl.productTableModel());
            basketImpl.lsProduct=productsImpl.productsList();

           if(tblCustomerSale.getSelectedRow()!=-1) {
               cmbcategory2.getModel().setSelectedItem(categoryProduct_Added);
               System.out.println(cmbcategory2.getSelectedIndex());
               tblproductSale.setModel(basketImpl.ProductSale_TableModel(categoryProduct_Added.getCatid(), ""));
               tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(1));
               tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(2));
           }


        }if (status==-1){
            lblUpdateError.setText("this product already been added");
        }
    }
    private void productDeleteClick(ActionEvent e) {

        int row = tblProduct.getSelectedRow();
        if(row!=-1){
            int pid = (Integer) tblProduct.getValueAt(row, 0);
            if (salesImpl.isContainProduct(pid)){
                JOptionPane.showMessageDialog(this,"This Product not deleted because Basket have a sale ",
                        "Warning!",  JOptionPane.WARNING_MESSAGE);
            }
            else{
            int answer = JOptionPane.showConfirmDialog(this,
                    "Are you sure that you want to customer."
                    , "Delete Process", JOptionPane.YES_NO_OPTION);
            if (answer == 0) {


                int catid= (Integer) tblProduct.getValueAt(row,1);
                int status = productsImpl.productDelete(pid);
                if (status > 0) {
                    tblProduct.clearSelection();
                    textboxProdUPDClear();
                    tblProduct.setModel(productsImpl.productTableModel());
                    basketImpl.lsProduct = productsImpl.productsList();
                    if(tblCustomerSale.getSelectedRow()!=-1){
                        Categories c = (Categories) cmbProductUpdate.getSelectedItem();
                        categoryId_SelectedProduct = c.getCatid();
                        String categoryName=c.getCatecoryname();
                        String catDescription=c.getCategoryDescription();
                        Categories categoryProduct_Deleted=new Categories(categoryId_SelectedProduct,categoryName,catDescription);
                        cmbcategory2.getModel().setSelectedItem(categoryProduct_Deleted);
                        tblproductSale.setModel(basketImpl.ProductSale_TableModel(catid, ""));
                        tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(1));
                        tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(2));
                        categoryProduct_Deleted=null;
                        }
                }

            }}
        }else lblUpdateError.setText("Please choose a product from table");

    }


    private void btnProductUpdateClick(ActionEvent e) {

        Products p= fncDataValidProductUPD();
        if (p!=null){
            int status= productsImpl.productUpdate(p);
            if(status>0){
                tblProduct.clearSelection();
                textboxProdUPDClear();
                textboxProdAddClear();
                tblProduct.setModel(productsImpl.productTableModel());
                basketImpl.lsProduct=productsImpl.productsList();
                if(tblCustomerSale.getSelectedRow()!=-1){
                cmbcategory2.getModel().setSelectedItem(categoryProduct_Added);
                tblproductSale.setModel(basketImpl.ProductSale_TableModel(categoryId_SelectedProductUpdate,""));
                tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(1));
                tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(2));}
            }else {
                lblUpdateError.setText("ınsert error");
            }
        }

    }
    private void tblProductMouseReleased(MouseEvent e) {
        int row = tblProduct.getSelectedRow();
        int catid= (Integer) tblProduct.getValueAt(row,1);
        String productname= String.valueOf(tblProduct.getValueAt(row,2));
        String purchaseprice= String.valueOf(tblProduct.getValueAt(row,3));
        String stockquantity= String.valueOf(tblProduct.getValueAt(row,4));
        String saleprice= String.valueOf(tblProduct.getValueAt(row,5));

        txtProductUpdateName.setText(productname);
        txtProductUpdatePurcPrice.setText(purchaseprice);
        txtProductUpdateStockQua.setText(stockquantity);
        txtProductUpdateSalePrice.setText(saleprice);
        cmbProductUpdate.setModel(categoriesImpl.cmbmodel(catid));
    }

    private void cmbProductUpdateMouseClicked(MouseEvent e) {
        cmbProductUpdate.setModel(categoriesImpl.cmbmodel());
    }


    private void cmbcategory2Click(ActionEvent e) {
        if(cmbcategory2.getSelectedIndex()>-1){
            Categories selected = (Categories) cmbcategory2.getSelectedItem();

            categoryId_SelectedBasket = selected.catid;
            tblproductSale.setModel(basketImpl.ProductSale_TableModel(categoryId_SelectedBasket, ""));
            tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(1));
            tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(2));}
    }


    private void txtSearchProduct3KeyReleased(KeyEvent e) {
        String data = txtSearchProduct3.getText().trim();
        Categories selected = (Categories) cmbcategory2.getSelectedItem();
        int catid = selected.getCatid();

        tblproductSale.setModel(basketImpl.ProductSale_TableModel(catid, data));
        tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(1));
        tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(2));
    }


    private void txtSearchCustomerKeyReleased(KeyEvent e) {

        String data = txtSearchCustomer.getText().trim();
        tblCustomerSale.setModel(basketImpl.customerTableModelBasket(data));
        tblCustomerSale.removeColumn(tblCustomerSale.getColumnModel().getColumn(0));
    }


    public SpinnerModel quantitySale_SpinnerModel() {
        int row = tblproductSale.getSelectedRow();
        int quantity = (Integer) tblproductSale.getValueAt(row, 2);
        if (quantity != 0) {
            lblSaleError.setText("");
            String[] str = new String[quantity];
            for (int i = 1; i <= str.length; i++) {
                String s = String.valueOf(i);
                str[i - 1] = s;
            }
            SpinnerModel spinnerModel = new SpinnerListModel(str);
            return spinnerModel;
        } else {
            lblSaleError.setText("This product is out of stock. Not added to basket.");
        }
        return null;
    }

    private void tblCustomerSaleMouseReleased(MouseEvent e) {


        cmbcategory2.setEnabled(true);
        cmbcategory2.setModel(categoriesImpl.cmbmodel());
        tblproductSale.setEnabled(true);
        txtSearchProduct3.setEditable(true);

    }

    private void tblproductSaleMouseReleased(MouseEvent e) {
        SpinnerModel spinnerModel = quantitySale_SpinnerModel();
        if (spinnerModel != null) {
            btnAddBucket.setEnabled(true);
            spinnerquantity.setModel(quantitySale_SpinnerModel());
            spinnerquantity.setEnabled(true);
        } else {
            spinnerquantity.setEnabled(false);
            btnAddBucket.setEnabled(false);
        }

    }


    private void btnAddBucketClick(ActionEvent e) {
        int row1 = tblCustomerSale.getSelectedRow();
        int row2 = tblproductSale.getSelectedRow();


        if (row1 == -1) {
            lblSaleError.setText("Please select a customer from customer table!!!");
        } else if (row2 == -1) {
            lblSaleError.setText("Please select a product from product table !!! ");
        } else {
            lblSaleError.setText("");
            int stockquantity = (int) tblproductSale.getValueAt(row2, 2);
            int selectedIdCustomer = (Integer) tblCustomerSale.getModel().getValueAt(row1, 0);
            int selectedIdProduct = (Integer) tblproductSale.getValueAt(row2, 0);
            String date = Date();
            txtDateSale.setText(date);
            String countString = spinnerquantity.getValue().toString();
            int count = Integer.parseInt(countString);
            Basket basket = new Basket(0, selectedIdCustomer, selectedIdProduct, date, count, 0, null);
            int status = basketImpl.insertBasket(basket);
            if (status > 0) {
                Categories selected = (Categories) cmbcategory2.getSelectedItem();
                categoryId_SelectedBasket = selected.catid;
                basketImpl.lsProduct = productsImpl.productsList();
                tblproductSale.setModel(basketImpl.ProductSale_TableModel(categoryId_SelectedBasket, ""));
                tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(1));
                tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(2));

                tblCustomerSale.setEnabled(false);
                tblBasket.setModel(basketImpl.basketTableModel(selectedIdCustomer));
                tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(0));
                tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(0));
                tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(0));
                tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(3));

                setLblSaleTotal();
                tblProduct.setModel(productsImpl.productTableModel());

            }

        }

    }


    private void btnDeleteBucket(ActionEvent e) {
        int row = tblBasket.getSelectedRow();

        if (row == -1) {
            lblSaleError.setText("Select row from basket table.");
        } else {
            lblSaleError.setText("");
            int selectedId = (Integer) tblBasket.getModel().getValueAt(row, 0);
            int selectedCid = (Integer) tblBasket.getModel().getValueAt(row, 1);
            int answer = JOptionPane.showConfirmDialog(this,
                    "Are you sure that you want to remove from basket."
                    , "Delete Process", JOptionPane.YES_NO_OPTION);
            if (answer == 0) {
                int status = basketImpl.deleteBasket(selectedId);
                if (status > 0) {

                    tblBasket.setModel(basketImpl.basketTableModel(selectedCid));
                    tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(0));
                    tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(0));
                    tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(0));
                    tblBasket.removeColumn(tblBasket.getColumnModel().getColumn(3));
                    Categories selectedItem = (Categories) cmbcategory2.getSelectedItem();
                    categoryId_SelectedBasket = selectedItem.catid;
                    basketImpl.lsProduct = productsImpl.productsList();
                    tblproductSale.setModel(basketImpl.ProductSale_TableModel(categoryId_SelectedBasket, ""));
                    tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(1));
                    tblproductSale.removeColumn(tblproductSale.getColumnModel().getColumn(2));
                    setLblSaleTotal();
                    tblProduct.setModel(productsImpl.productTableModel());

                }
            }

        }
    }

    public static String Date() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        return date;
    }

    private void btnCompleteSaleClick(ActionEvent e) {
        String date1 = "1900-12-01";
        String date2 = "2500-12-01";
        int row = tblCustomerSale.getSelectedRow();

        Double total = Double.parseDouble(lblSaleTotal.getText());
        if (total == 0) {
            lblSaleError.setText("There is not product at basket..");
        } else {
            int selectedIdCustomer = (Integer) tblCustomerSale.getModel().getValueAt(row, 0);
            Sales sales = new Sales(0, selectedIdCustomer, total, Date());
            int status = salesImpl.salesInsert(sales);
            if (status > 0) {
                lblSaleError.setText("");
                JOptionPane.showMessageDialog(this, "Sale completed successfully.");
                List<Basket> basketList = getBasketListfinally();
                int statusUpdateBasket = salesImpl.updateBasket(basketList);
                String totalProfit=String.valueOf(salesImpl.CalculateTotalProfit(salesImpl.baketReportList(date1,date2)));
                lblRepotsProfit.setText(totalProfit);
                s.setLstReport(s.baketReportList(date1,date2));
                if (statusUpdateBasket == basketList.size()) {
                    clearSelectionSale();

                }
            }
        }

    }

    public void deleteAllRow(JTable table) {
        DefaultTableModel dm = (DefaultTableModel) table.getModel();
        int rowCount = dm.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }

    public List<Basket> getBasketListfinally() {
        int rowcount = tblBasket.getRowCount();
        List<Basket> basketList = new ArrayList<>();
        for (int i = 0; i < rowcount; i++) {
            int bid = (Integer) tblBasket.getModel().getValueAt(i, 0);
            int cid = (Integer) tblBasket.getModel().getValueAt(i, 1);
            int pid = (Integer) tblBasket.getModel().getValueAt(i, 2);
            String productName = (String) tblBasket.getModel().getValueAt(i, 3);
            int count = (Integer) tblBasket.getModel().getValueAt(i, 4);
            // Double price=(Double)tblBasket.getModel().getValueAt(i,5);
            Basket basket = new Basket(bid, cid, pid, Date(), count, 0, null);

            basketList.add(basket);

        }

        return basketList;
    }


    public void setLblSaleTotal() {
        Double totalBasket = 0.0;
        int rowcount = tblBasket.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            double price = (Double) tblBasket.getValueAt(i, 2);
            int count1 = (Integer) tblBasket.getValueAt(i, 1);
            double c = count1 * price;
            totalBasket = totalBasket + c;
        }
        String totalS = String.valueOf(totalBasket);
        lblSaleTotal.setText(totalS);
        //totalBasket =0.0;
    }
    private void btnCancelSaleClick(ActionEvent e) {
        clearSelectionSale();
    }
    void clearSelectionSale(){
        tblCustomerSale.setEnabled(true);
        tblCustomerSale.clearSelection();
        cmbcategory2.setSelectedIndex(-1);
        cmbcategory2.setEnabled(false);
        spinnerquantity.setEnabled(false);
        lblSaleTotal.setText("");
        deleteAllRow(tblproductSale);
        deleteAllRow(tblBasket);
    }

    private void txtReportSearchKeyReleased(KeyEvent e) {
        String data = txtReportSearch.getText().trim();
        Boolean customer = rdbCustomer.isSelected();
        Boolean category = rdbCategory.isSelected();
        Boolean product = rrdbProduct.isSelected();

        if (customer) {
            tblReport.setModel(s.bakettableModel_Report(data, "customer"));
            lblReportError.setText("");

        } else if (category) {
            tblReport.setModel(s.bakettableModel_Report(data, "category"));
            lblReportError.setText("");
        } else if (product) {
            tblReport.setModel(s.bakettableModel_Report(data, "product"));
            lblReportError.setText("");
        } else {
            lblReportError.setText("Please select the area to be searched.");
        }
    }

    private void datePicker1PropertyChange(PropertyChangeEvent e) {

        if (datePicker1.getDate() != null ) {
            date1 = datePicker1.getDate().toString();
            s.setLstReport(s.baketReportList(date1,date2));
        }
    }

    private void datePicker2PropertyChange(PropertyChangeEvent e) {
        if(datePicker2.getDate() != null){
            date2 = datePicker2.getDate().toString();
            s.setLstReport(s.baketReportList(date1,date2));
        }
    }


    private void tabPaneCustomerStateChanged(ChangeEvent e) {
        if(tabPaneCustomer.getSelectedIndex()==4){
            txtReportSearch.setText("");
            deleteAllRow(tblReport);
        }
    }
    private void btnPdfCreateReportClick(ActionEvent e) {
        PdfCreate();
    }

    public void PdfCreate() {
        int rowCount = tblReport.getRowCount();
        try {
            String imageFile = "images\\logo.jpg";
            ImageData data = ImageDataFactory.create(imageFile);
            Image image=new Image(data);

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter("Report.pdf"));
            Document doc = new Document(pdfDoc);

            doc.add(image);
            DeviceRgb color1 = WebColors.getRGBColor("orange");
            Paragraph preface = new Paragraph(new Text("Report of Sales").setBold().setBackgroundColor(color1));
            DeviceRgb color = WebColors.getRGBColor("white");

            preface.setTextAlignment(TextAlignment.CENTER).setFontColor(color).setFontSize(16);
            doc.add(preface);
            doc.add(new Paragraph(""));
            doc.add(new Paragraph(""));

            //AreaBreak aB = new AreaBreak();
            //doc.add(aB);

            Table table = new Table(new float[]{60, 60, 75, 100, 50, 50, 85, 50});
            table.addCell(new Cell().add(new Paragraph("First Name")))
                    .addCell("Second Name")
                    .addCell("Category Name")
                    .addCell("Product Name")
                    .addCell("Sale Quantity")
                    .addCell("Sale Price")
                    .addCell("Sale Date")
                    .addCell("Total");
            for (int i = 0; i < rowCount; i++) {
                String name = (String) tblReport.getValueAt(i, 0);
                String surname = (String) tblReport.getValueAt(i, 1);
                String categoryName = (String) tblReport.getValueAt(i, 2);
                String productName = (String) tblReport.getValueAt(i, 3);
                int saleQuantity = ((Number) tblReport.getValueAt(i, 4)).intValue();
                String saleQuantityString = String.valueOf(saleQuantity);
                double price = (Double) tblReport.getValueAt(i, 5);
                String priceString = String.valueOf(price);
                String date = (String) tblReport.getValueAt(i, 6);
                double total = (Double) tblReport.getValueAt(i, 7);
                String totalString = String.valueOf(total);


                table.addCell(new Cell().add(new Paragraph(name)))
                        .addCell(surname).
                        addCell(categoryName).
                        addCell(productName).
                        addCell(saleQuantityString).
                        addCell(priceString).
                        addCell(date).
                        addCell(totalString);

            }
            doc.add(table.setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
            doc.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void btnChangePasswordClick(ActionEvent e) {
        String password = Util.MD5(String.valueOf(txtOldPassword.getPassword()));
        String newPassword = String.valueOf(txtNewPassword.getPassword());
        String newPasswordAgain = String.valueOf(txtNewPasswordAgain.getPassword());

        if (password.equals("")) {
            lblUserSettingsError.setText("Password Empty");
            txtOldPassword.requestFocus();

        }else if(newPassword.equals("")){
            lblUserSettingsError.setText("New Password Empty");
            txtNewPassword.requestFocus();
        }else if(newPasswordAgain.equals("")){
            lblUserSettingsError.setText("New Password Again Empty");
            txtNewPasswordAgain.requestFocus();
        }else if(!newPassword.equals(newPasswordAgain)){
            lblUserSettingsError.setText("New passwords are not the same");
            txtNewPassword.requestFocus();
        }else{
            if (UserImpl.loginPassword.equals(password)){
                int answer=JOptionPane.showConfirmDialog(this,"Change password. Are you sure that you continue?",
                        "Change Password",JOptionPane.YES_NO_OPTION);
                if(answer==0)
                {
                    userImpl.changeUserPassword(newPassword);
                    txtOldPassword.setText("");
                    txtNewPassword.setText("");
                    txtNewPasswordAgain.setText("");
                }
            }

        }
    }







    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabPaneCustomer = new JTabbedPane();
        panel2 = new JPanel();
        panel18 = new JPanel();
        scrollPane8 = new JScrollPane();
        tblCustomer = new JTable();
        panel20 = new JPanel();
        label19 = new JLabel();
        label20 = new JLabel();
        label21 = new JLabel();
        label22 = new JLabel();
        txtCustomerAddName = new JTextField();
        txtCustomerAddSurname = new JTextField();
        txtCustomerAddPhone = new JTextField();
        txtCustomerAddAddress = new JTextField();
        btnNewCustomerAdd = new JButton();
        txtCustomerAddEmail = new JTextField();
        label27 = new JLabel();
        lblCustomerError = new JLabel();
        panel21 = new JPanel();
        label23 = new JLabel();
        label24 = new JLabel();
        label25 = new JLabel();
        label26 = new JLabel();
        txtCustomerEditName = new JTextField();
        txtCustomerEditSurname = new JTextField();
        txtCustomerEditPhone = new JTextField();
        txtCustomerEditAdress = new JTextField();
        btnCustomerUpdate = new JButton();
        btnCustomerDelete = new JButton();
        txtCustomerEditEmail = new JTextField();
        label28 = new JLabel();
        lblCustomerError2 = new JLabel();
        panel5 = new JPanel();
        panel7 = new JPanel();
        scrollPane1 = new JScrollPane();
        tblCategory = new JTable();
        panel9 = new JPanel();
        label3 = new JLabel();
        txtCategoryName = new JTextField();
        label4 = new JLabel();
        scrollPane3 = new JScrollPane();
        txtCategoryDescription = new JTextArea();
        btnCategoryAdd = new JButton();
        btnCategoryDelete = new JButton();
        btnCategoryUpdate = new JButton();
        lblError = new JLabel();
        panel4 = new JPanel();
        panel14 = new JPanel();
        scrollPane7 = new JScrollPane();
        tblProduct = new JTable();
        panel15 = new JPanel();
        panel16 = new JPanel();
        label9 = new JLabel();
        txtProductAddSalePrice = new JTextField();
        btnProductAdd = new JButton();
        txtProductAddStockQua = new JTextField();
        label10 = new JLabel();
        label11 = new JLabel();
        txtProductAddPurcPrice = new JTextField();
        label12 = new JLabel();
        cmbPrpductAdd = new JComboBox();
        label13 = new JLabel();
        txtProductAddName = new JTextField();
        lbllAddError = new JLabel();
        panel17 = new JPanel();
        label14 = new JLabel();
        txtProductUpdateSalePrice = new JTextField();
        label15 = new JLabel();
        txtProductUpdateStockQua = new JTextField();
        label16 = new JLabel();
        txtProductUpdatePurcPrice = new JTextField();
        label17 = new JLabel();
        cmbProductUpdate = new JComboBox();
        txtProductUpdateName = new JTextField();
        label18 = new JLabel();
        btnProductUpdate = new JButton();
        lblUpdateError = new JLabel();
        productDelete = new JButton();
        panel3 = new JPanel();
        btnCompleteSale = new JButton();
        lbl = new JLabel();
        lblSaleTotal = new JLabel();
        lblSaleError = new JLabel();
        panel8 = new JPanel();
        scrollPane2 = new JScrollPane();
        tblBasket = new JTable();
        btnAddBucket = new JButton();
        btnDeleteBucket = new JButton();
        label8 = new JLabel();
        panel12 = new JPanel();
        scrollPane5 = new JScrollPane();
        tblCustomerSale = new JTable();
        label7 = new JLabel();
        txtSearchCustomer = new JTextField();
        spinnerquantity = new JSpinner();
        label5 = new JLabel();
        cmbcategory2 = new JComboBox();
        panel11 = new JPanel();
        scrollPane4 = new JScrollPane();
        tblproductSale = new JTable();
        label6 = new JLabel();
        txtSearchProduct3 = new JTextField();
        label29 = new JLabel();
        txtDateSale = new JTextField();
        btnCancelSale = new JButton();
        panel1 = new JPanel();
        panel19 = new JPanel();
        label1 = new JLabel();
        lblRepotsProfit = new JLabel();
        panel13 = new JPanel();
        scrollPane6 = new JScrollPane();
        tblReport = new JTable();
        panel22 = new JPanel();
        rdbCustomer = new JRadioButton();
        rrdbProduct = new JRadioButton();
        rdbCategory = new JRadioButton();
        txtReportSearch = new JTextField();
        datePicker2 = new DatePicker();
        datePicker1 = new DatePicker();
        label2 = new JLabel();
        label30 = new JLabel();
        label31 = new JLabel();
        label32 = new JLabel();
        lblReportError = new JLabel();
        btnPdfCreateReport = new JButton();
        panel6 = new JPanel();
        panel23 = new JPanel();
        label33 = new JLabel();
        txtOldPassword = new JPasswordField();
        txtNewPasswordAgain = new JPasswordField();
        label34 = new JLabel();
        txtNewPassword = new JPasswordField();
        label35 = new JLabel();
        btnChangePassword = new JButton();
        lblUserSettingsError = new JLabel();
        label36 = new JLabel();
        label37 = new JLabel();

        //======== this ========
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();

        //======== tabPaneCustomer ========
        {
            tabPaneCustomer.setFont(tabPaneCustomer.getFont().deriveFont(tabPaneCustomer.getFont().getStyle() | Font.BOLD, tabPaneCustomer.getFont().getSize() + 5f));
            tabPaneCustomer.setForeground(Color.white);
            tabPaneCustomer.setBackground(new Color(0, 71, 151));
            tabPaneCustomer.addChangeListener(e -> tabPaneCustomerStateChanged(e));

            //======== panel2 ========
            {
                panel2.setBackground(Color.lightGray);

                //======== panel18 ========
                {
                    panel18.setBorder(new TitledBorder(new LineBorder(new Color(0, 49, 186), 2, true), "Customer List", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16), Color.darkGray));
                    panel18.setBackground(Color.lightGray);

                    //======== scrollPane8 ========
                    {

                        //---- tblCustomer ----
                        tblCustomer.setBackground(SystemColor.window);
                        tblCustomer.setForeground(Color.darkGray);
                        tblCustomer.setFont(tblCustomer.getFont().deriveFont(tblCustomer.getFont().getSize() + 1f));
                        tblCustomer.setSelectionBackground(new Color(255, 154, 47));
                        tblCustomer.setSelectionForeground(Color.white);
                        tblCustomer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        tblCustomer.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                tblCustomerMouseReleased(e);
                            }
                        });
                        scrollPane8.setViewportView(tblCustomer);
                    }

                    GroupLayout panel18Layout = new GroupLayout(panel18);
                    panel18.setLayout(panel18Layout);
                    panel18Layout.setHorizontalGroup(
                        panel18Layout.createParallelGroup()
                            .addGroup(panel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane8)
                                .addContainerGap())
                    );
                    panel18Layout.setVerticalGroup(
                        panel18Layout.createParallelGroup()
                            .addGroup(panel18Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane8, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
                    );
                }

                //======== panel20 ========
                {
                    panel20.setBorder(new LineBorder(new Color(0, 49, 186), 2));
                    panel20.setBackground(Color.lightGray);

                    //---- label19 ----
                    label19.setText("Customer Name");
                    label19.setFont(label19.getFont().deriveFont(label19.getFont().getStyle() | Font.BOLD, label19.getFont().getSize() + 2f));
                    label19.setBackground(Color.darkGray);
                    label19.setForeground(Color.darkGray);

                    //---- label20 ----
                    label20.setText("Surname");
                    label20.setBackground(Color.darkGray);
                    label20.setFont(label20.getFont().deriveFont(label20.getFont().getStyle() | Font.BOLD, label20.getFont().getSize() + 2f));
                    label20.setForeground(Color.darkGray);

                    //---- label21 ----
                    label21.setText("Phone");
                    label21.setFont(label21.getFont().deriveFont(label21.getFont().getStyle() | Font.BOLD, label21.getFont().getSize() + 2f));
                    label21.setBackground(Color.darkGray);
                    label21.setForeground(Color.darkGray);

                    //---- label22 ----
                    label22.setText("Address");
                    label22.setFont(label22.getFont().deriveFont(label22.getFont().getStyle() | Font.BOLD, label22.getFont().getSize() + 2f));
                    label22.setForeground(Color.darkGray);

                    //---- txtCustomerAddName ----
                    txtCustomerAddName.setFont(txtCustomerAddName.getFont().deriveFont(txtCustomerAddName.getFont().getSize() + 1f));

                    //---- txtCustomerAddSurname ----
                    txtCustomerAddSurname.setFont(txtCustomerAddSurname.getFont().deriveFont(txtCustomerAddSurname.getFont().getSize() + 1f));

                    //---- txtCustomerAddPhone ----
                    txtCustomerAddPhone.setFont(txtCustomerAddPhone.getFont().deriveFont(txtCustomerAddPhone.getFont().getSize() + 1f));

                    //---- txtCustomerAddAddress ----
                    txtCustomerAddAddress.setFont(txtCustomerAddAddress.getFont().deriveFont(txtCustomerAddAddress.getFont().getSize() + 1f));

                    //---- btnNewCustomerAdd ----
                    btnNewCustomerAdd.setText("Add");
                    btnNewCustomerAdd.setFont(btnNewCustomerAdd.getFont().deriveFont(btnNewCustomerAdd.getFont().getStyle() | Font.BOLD, btnNewCustomerAdd.getFont().getSize() + 6f));
                    btnNewCustomerAdd.setForeground(Color.white);
                    btnNewCustomerAdd.setBackground(new Color(255, 154, 47));
                    btnNewCustomerAdd.setIcon(null);
                    btnNewCustomerAdd.addActionListener(e -> btnNewCustomerAdd(e));

                    //---- txtCustomerAddEmail ----
                    txtCustomerAddEmail.setFont(txtCustomerAddEmail.getFont().deriveFont(txtCustomerAddEmail.getFont().getSize() + 1f));

                    //---- label27 ----
                    label27.setText("E-mail");
                    label27.setFont(label27.getFont().deriveFont(label27.getFont().getStyle() | Font.BOLD, label27.getFont().getSize() + 2f));
                    label27.setForeground(Color.darkGray);

                    //---- lblCustomerError ----
                    lblCustomerError.setForeground(new Color(204, 0, 0));
                    lblCustomerError.setFont(lblCustomerError.getFont().deriveFont(lblCustomerError.getFont().getSize() + 2f));
                    lblCustomerError.setHorizontalAlignment(SwingConstants.CENTER);

                    GroupLayout panel20Layout = new GroupLayout(panel20);
                    panel20.setLayout(panel20Layout);
                    panel20Layout.setHorizontalGroup(
                        panel20Layout.createParallelGroup()
                            .addGroup(panel20Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel20Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(GroupLayout.Alignment.LEADING, panel20Layout.createSequentialGroup()
                                        .addGroup(panel20Layout.createParallelGroup()
                                            .addComponent(label19, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label20, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label21, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label22, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel20Layout.createParallelGroup()
                                            .addComponent(txtCustomerAddSurname, GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtCustomerAddAddress)
                                            .addComponent(txtCustomerAddPhone)
                                            .addComponent(txtCustomerAddName)))
                                    .addGroup(GroupLayout.Alignment.LEADING, panel20Layout.createSequentialGroup()
                                        .addComponent(label27, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(txtCustomerAddEmail)))
                                .addContainerGap())
                            .addGroup(GroupLayout.Alignment.TRAILING, panel20Layout.createSequentialGroup()
                                .addGap(141, 141, 141)
                                .addComponent(btnNewCustomerAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(15, 15, 15))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel20Layout.createSequentialGroup()
                                .addContainerGap(113, Short.MAX_VALUE)
                                .addComponent(lblCustomerError, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                    panel20Layout.setVerticalGroup(
                        panel20Layout.createParallelGroup()
                            .addGroup(panel20Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(panel20Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label19)
                                    .addComponent(txtCustomerAddName, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel20Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label20)
                                    .addComponent(txtCustomerAddSurname, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel20Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label21)
                                    .addComponent(txtCustomerAddPhone, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel20Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label22)
                                    .addComponent(txtCustomerAddAddress, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel20Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label27)
                                    .addComponent(txtCustomerAddEmail, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCustomerError, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNewCustomerAdd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                    );
                }

                //======== panel21 ========
                {
                    panel21.setBorder(new LineBorder(new Color(0, 49, 186), 2));
                    panel21.setBackground(Color.lightGray);

                    //---- label23 ----
                    label23.setText("Customer Name");
                    label23.setFont(label23.getFont().deriveFont(label23.getFont().getStyle() | Font.BOLD, label23.getFont().getSize() + 2f));
                    label23.setBackground(Color.darkGray);

                    //---- label24 ----
                    label24.setText("Surname");
                    label24.setFont(label24.getFont().deriveFont(label24.getFont().getStyle() | Font.BOLD, label24.getFont().getSize() + 2f));
                    label24.setBackground(Color.darkGray);

                    //---- label25 ----
                    label25.setText("Phone");
                    label25.setBackground(Color.darkGray);
                    label25.setFont(label25.getFont().deriveFont(label25.getFont().getStyle() | Font.BOLD, label25.getFont().getSize() + 2f));

                    //---- label26 ----
                    label26.setText("Address");
                    label26.setBackground(Color.darkGray);
                    label26.setFont(label26.getFont().deriveFont(label26.getFont().getStyle() | Font.BOLD, label26.getFont().getSize() + 2f));

                    //---- txtCustomerEditName ----
                    txtCustomerEditName.setFont(txtCustomerEditName.getFont().deriveFont(txtCustomerEditName.getFont().getSize() + 1f));

                    //---- txtCustomerEditSurname ----
                    txtCustomerEditSurname.setFont(txtCustomerEditSurname.getFont().deriveFont(txtCustomerEditSurname.getFont().getSize() + 1f));

                    //---- txtCustomerEditPhone ----
                    txtCustomerEditPhone.setFont(txtCustomerEditPhone.getFont().deriveFont(txtCustomerEditPhone.getFont().getSize() + 1f));

                    //---- txtCustomerEditAdress ----
                    txtCustomerEditAdress.setFont(txtCustomerEditAdress.getFont().deriveFont(txtCustomerEditAdress.getFont().getSize() + 1f));

                    //---- btnCustomerUpdate ----
                    btnCustomerUpdate.setText("Update");
                    btnCustomerUpdate.setFont(btnCustomerUpdate.getFont().deriveFont(btnCustomerUpdate.getFont().getStyle() | Font.BOLD, btnCustomerUpdate.getFont().getSize() + 6f));
                    btnCustomerUpdate.setForeground(Color.white);
                    btnCustomerUpdate.setBackground(new Color(255, 154, 47));
                    btnCustomerUpdate.setIcon(null);
                    btnCustomerUpdate.addActionListener(e -> btnCustomerUpdate(e));

                    //---- btnCustomerDelete ----
                    btnCustomerDelete.setText("Delete");
                    btnCustomerDelete.setFont(btnCustomerDelete.getFont().deriveFont(btnCustomerDelete.getFont().getStyle() | Font.BOLD, btnCustomerDelete.getFont().getSize() + 6f));
                    btnCustomerDelete.setForeground(Color.white);
                    btnCustomerDelete.setBackground(new Color(255, 154, 47));
                    btnCustomerDelete.setIcon(null);
                    btnCustomerDelete.addActionListener(e -> btnCustomerDelete(e));

                    //---- txtCustomerEditEmail ----
                    txtCustomerEditEmail.setFont(txtCustomerEditEmail.getFont().deriveFont(txtCustomerEditEmail.getFont().getSize() + 1f));

                    //---- label28 ----
                    label28.setText("Email");
                    label28.setBackground(Color.darkGray);
                    label28.setFont(label28.getFont().deriveFont(label28.getFont().getStyle() | Font.BOLD, label28.getFont().getSize() + 2f));

                    //---- lblCustomerError2 ----
                    lblCustomerError2.setForeground(new Color(204, 0, 0));
                    lblCustomerError2.setFont(lblCustomerError2.getFont().deriveFont(lblCustomerError2.getFont().getSize() + 2f));
                    lblCustomerError2.setHorizontalAlignment(SwingConstants.CENTER);

                    GroupLayout panel21Layout = new GroupLayout(panel21);
                    panel21.setLayout(panel21Layout);
                    panel21Layout.setHorizontalGroup(
                        panel21Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel21Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblCustomerError2, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel21Layout.createSequentialGroup()
                                                .addComponent(btnCustomerUpdate, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCustomerDelete, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(GroupLayout.Alignment.LEADING, panel21Layout.createSequentialGroup()
                                        .addGroup(panel21Layout.createParallelGroup()
                                            .addComponent(label23, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label24, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label25, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label26, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label28, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel21Layout.createParallelGroup()
                                            .addComponent(txtCustomerEditEmail, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                            .addComponent(txtCustomerEditAdress, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                            .addComponent(txtCustomerEditPhone)
                                            .addComponent(txtCustomerEditSurname)
                                            .addComponent(txtCustomerEditName))))
                                .addGap(15, 15, 15))
                    );
                    panel21Layout.setVerticalGroup(
                        panel21Layout.createParallelGroup()
                            .addGroup(panel21Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label23)
                                    .addComponent(txtCustomerEditName, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17)
                                .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label24)
                                    .addComponent(txtCustomerEditSurname, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel21Layout.createParallelGroup()
                                    .addComponent(label25)
                                    .addComponent(txtCustomerEditPhone, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel21Layout.createParallelGroup()
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(label26))
                                    .addGroup(panel21Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCustomerEditAdress, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label28)
                                    .addComponent(txtCustomerEditEmail, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCustomerError2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel21Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCustomerUpdate, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCustomerDelete, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23))
                    );
                }

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(panel18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(panel20, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(panel21, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addContainerGap())
                );
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(panel18, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(panel2Layout.createParallelGroup()
                                .addComponent(panel21, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
                );
            }
            tabPaneCustomer.addTab("Customers", panel2);

            //======== panel5 ========
            {
                panel5.setBackground(Color.lightGray);

                //======== panel7 ========
                {
                    panel7.setBorder(new TitledBorder(new LineBorder(new Color(0, 57, 199), 2), "Category List", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16), Color.darkGray));
                    panel7.setBackground(Color.lightGray);

                    //======== scrollPane1 ========
                    {

                        //---- tblCategory ----
                        tblCategory.setFont(tblCategory.getFont().deriveFont(tblCategory.getFont().getSize() + 2f));
                        tblCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        tblCategory.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                tblCategoryMouseReleased(e);
                            }
                        });
                        scrollPane1.setViewportView(tblCategory);
                    }

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 822, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panel7Layout.setVerticalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel7Layout.createSequentialGroup()
                                .addContainerGap(29, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))
                    );
                }

                //======== panel9 ========
                {
                    panel9.setBorder(new TitledBorder(new LineBorder(new Color(0, 68, 181), 2), "Category Editing", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16), Color.darkGray));
                    panel9.setBackground(Color.lightGray);

                    //---- label3 ----
                    label3.setText("Category Name");
                    label3.setForeground(Color.darkGray);
                    label3.setFont(label3.getFont().deriveFont(label3.getFont().getStyle() | Font.BOLD, label3.getFont().getSize() + 3f));

                    //---- txtCategoryName ----
                    txtCategoryName.setFont(txtCategoryName.getFont().deriveFont(txtCategoryName.getFont().getSize() + 2f));
                    txtCategoryName.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtCategoryNameKeyReleased(e);
                        }
                    });

                    //---- label4 ----
                    label4.setText("Description");
                    label4.setFont(label4.getFont().deriveFont(label4.getFont().getStyle() | Font.BOLD, label4.getFont().getSize() + 3f));
                    label4.setForeground(Color.darkGray);

                    //======== scrollPane3 ========
                    {

                        //---- txtCategoryDescription ----
                        txtCategoryDescription.setFont(txtCategoryDescription.getFont().deriveFont(txtCategoryDescription.getFont().getSize() + 2f));
                        txtCategoryDescription.addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyReleased(KeyEvent e) {
                                txtCategoryDescriptionKeyReleased(e);
                            }
                        });
                        scrollPane3.setViewportView(txtCategoryDescription);
                    }

                    //---- btnCategoryAdd ----
                    btnCategoryAdd.setText("ADD");
                    btnCategoryAdd.setBackground(new Color(255, 154, 47));
                    btnCategoryAdd.setFont(btnCategoryAdd.getFont().deriveFont(btnCategoryAdd.getFont().getStyle() | Font.BOLD, btnCategoryAdd.getFont().getSize() + 6f));
                    btnCategoryAdd.setForeground(Color.white);
                    btnCategoryAdd.addActionListener(e -> btnCategoryAdd(e));

                    //---- btnCategoryDelete ----
                    btnCategoryDelete.setText("DELETE");
                    btnCategoryDelete.setBackground(new Color(255, 154, 47));
                    btnCategoryDelete.setFont(btnCategoryDelete.getFont().deriveFont(btnCategoryDelete.getFont().getStyle() | Font.BOLD, btnCategoryDelete.getFont().getSize() + 6f));
                    btnCategoryDelete.setForeground(Color.white);
                    btnCategoryDelete.addActionListener(e -> btnCategoryDelete(e));

                    //---- btnCategoryUpdate ----
                    btnCategoryUpdate.setText("UPDATE");
                    btnCategoryUpdate.setBackground(new Color(255, 154, 47));
                    btnCategoryUpdate.setFont(btnCategoryUpdate.getFont().deriveFont(btnCategoryUpdate.getFont().getStyle() | Font.BOLD, btnCategoryUpdate.getFont().getSize() + 6f));
                    btnCategoryUpdate.setForeground(Color.white);
                    btnCategoryUpdate.addActionListener(e -> btnCategoryUpdate(e));

                    GroupLayout panel9Layout = new GroupLayout(panel9);
                    panel9.setLayout(panel9Layout);
                    panel9Layout.setHorizontalGroup(
                        panel9Layout.createParallelGroup()
                            .addGroup(panel9Layout.createSequentialGroup()
                                .addGap(218, 218, 218)
                                .addComponent(lblError, GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(236, Short.MAX_VALUE))
                            .addGroup(panel9Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(panel9Layout.createParallelGroup()
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(panel9Layout.createParallelGroup()
                                    .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCategoryName))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(panel9Layout.createParallelGroup()
                                    .addComponent(btnCategoryAdd, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel9Layout.createParallelGroup()
                                        .addComponent(btnCategoryUpdate, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCategoryDelete, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)))
                                .addGap(33, 33, 33))
                    );
                    panel9Layout.setVerticalGroup(
                        panel9Layout.createParallelGroup()
                            .addGroup(panel9Layout.createSequentialGroup()
                                .addContainerGap(14, Short.MAX_VALUE)
                                .addComponent(lblError)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCategoryAdd, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCategoryName, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel9Layout.createParallelGroup()
                                    .addGroup(panel9Layout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel9Layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addGroup(panel9Layout.createParallelGroup()
                                            .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel9Layout.createSequentialGroup()
                                                .addComponent(btnCategoryUpdate, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(btnCategoryDelete, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(28, Short.MAX_VALUE))
                    );
                }

                GroupLayout panel5Layout = new GroupLayout(panel5);
                panel5.setLayout(panel5Layout);
                panel5Layout.setHorizontalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel5Layout.createParallelGroup()
                                .addComponent(panel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel5Layout.createSequentialGroup()
                                    .addComponent(panel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addContainerGap())
                );
                panel5Layout.setVerticalGroup(
                    panel5Layout.createParallelGroup()
                        .addGroup(panel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(panel7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(panel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(27, Short.MAX_VALUE))
                );
            }
            tabPaneCustomer.addTab("Category Manegment", panel5);

            //======== panel4 ========
            {
                panel4.setBackground(Color.lightGray);

                //======== panel14 ========
                {
                    panel14.setBorder(new TitledBorder(new LineBorder(new Color(0, 44, 179), 2), "Product List", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14), Color.darkGray));
                    panel14.setBackground(Color.lightGray);

                    //======== scrollPane7 ========
                    {

                        //---- tblProduct ----
                        tblProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        tblProduct.setFont(tblProduct.getFont().deriveFont(tblProduct.getFont().getSize() + 1f));
                        tblProduct.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                tblProductMouseReleased(e);
                            }
                        });
                        scrollPane7.setViewportView(tblProduct);
                    }

                    //======== panel15 ========
                    {

                        GroupLayout panel15Layout = new GroupLayout(panel15);
                        panel15.setLayout(panel15Layout);
                        panel15Layout.setHorizontalGroup(
                            panel15Layout.createParallelGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                        );
                        panel15Layout.setVerticalGroup(
                            panel15Layout.createParallelGroup()
                                .addGap(0, 57, Short.MAX_VALUE)
                        );
                    }

                    GroupLayout panel14Layout = new GroupLayout(panel14);
                    panel14.setLayout(panel14Layout);
                    panel14Layout.setHorizontalGroup(
                        panel14Layout.createParallelGroup()
                            .addGroup(panel14Layout.createSequentialGroup()
                                .addComponent(panel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane7, GroupLayout.PREFERRED_SIZE, 821, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(16, Short.MAX_VALUE))
                    );
                    panel14Layout.setVerticalGroup(
                        panel14Layout.createParallelGroup()
                            .addGroup(panel14Layout.createSequentialGroup()
                                .addComponent(panel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 69, Short.MAX_VALUE))
                            .addComponent(scrollPane7, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    );
                }

                //======== panel16 ========
                {
                    panel16.setBorder(new TitledBorder(new LineBorder(new Color(0, 46, 190), 2), "New Product Add", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14), Color.darkGray));
                    panel16.setBackground(Color.lightGray);

                    //---- label9 ----
                    label9.setText("Sale Price");
                    label9.setFont(label9.getFont().deriveFont(label9.getFont().getStyle() | Font.BOLD, label9.getFont().getSize() + 2f));
                    label9.setForeground(Color.darkGray);

                    //---- txtProductAddSalePrice ----
                    txtProductAddSalePrice.setFont(txtProductAddSalePrice.getFont().deriveFont(txtProductAddSalePrice.getFont().getSize() + 2f));

                    //---- btnProductAdd ----
                    btnProductAdd.setText("Product Add");
                    btnProductAdd.setFont(btnProductAdd.getFont().deriveFont(btnProductAdd.getFont().getStyle() | Font.BOLD, btnProductAdd.getFont().getSize() + 6f));
                    btnProductAdd.setForeground(Color.white);
                    btnProductAdd.setBackground(new Color(255, 154, 47));
                    btnProductAdd.addActionListener(e -> btnProductAdd(e));

                    //---- txtProductAddStockQua ----
                    txtProductAddStockQua.setFont(txtProductAddStockQua.getFont().deriveFont(txtProductAddStockQua.getFont().getSize() + 2f));

                    //---- label10 ----
                    label10.setText("Stock Quantity");
                    label10.setFont(label10.getFont().deriveFont(label10.getFont().getStyle() | Font.BOLD, label10.getFont().getSize() + 2f));
                    label10.setForeground(Color.darkGray);

                    //---- label11 ----
                    label11.setText("Purchase Price");
                    label11.setFont(label11.getFont().deriveFont(label11.getFont().getStyle() | Font.BOLD, label11.getFont().getSize() + 2f));
                    label11.setForeground(Color.darkGray);

                    //---- txtProductAddPurcPrice ----
                    txtProductAddPurcPrice.setFont(txtProductAddPurcPrice.getFont().deriveFont(txtProductAddPurcPrice.getFont().getSize() + 2f));

                    //---- label12 ----
                    label12.setText("Category Name");
                    label12.setFont(label12.getFont().deriveFont(label12.getFont().getStyle() | Font.BOLD, label12.getFont().getSize() + 2f));
                    label12.setForeground(Color.darkGray);

                    //---- cmbPrpductAdd ----
                    cmbPrpductAdd.setFont(cmbPrpductAdd.getFont().deriveFont(cmbPrpductAdd.getFont().getSize() + 2f));

                    //---- label13 ----
                    label13.setText("Product Name");
                    label13.setFont(label13.getFont().deriveFont(label13.getFont().getStyle() | Font.BOLD, label13.getFont().getSize() + 2f));
                    label13.setForeground(Color.darkGray);

                    //---- txtProductAddName ----
                    txtProductAddName.setFont(txtProductAddName.getFont().deriveFont(txtProductAddName.getFont().getSize() + 2f));

                    //---- lbllAddError ----
                    lbllAddError.setText("TXTT");
                    lbllAddError.setFont(lbllAddError.getFont().deriveFont(lbllAddError.getFont().getStyle() | Font.BOLD, lbllAddError.getFont().getSize() + 2f));
                    lbllAddError.setForeground(new Color(204, 0, 0));

                    GroupLayout panel16Layout = new GroupLayout(panel16);
                    panel16.setLayout(panel16Layout);
                    panel16Layout.setHorizontalGroup(
                        panel16Layout.createParallelGroup()
                            .addGroup(panel16Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel16Layout.createParallelGroup()
                                    .addComponent(lbllAddError, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel16Layout.createSequentialGroup()
                                        .addGroup(panel16Layout.createParallelGroup()
                                            .addComponent(label12, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label13))
                                        .addGap(35, 35, 35)
                                        .addGroup(panel16Layout.createParallelGroup()
                                            .addComponent(txtProductAddName, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                            .addComponent(cmbPrpductAdd, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                                    .addGroup(panel16Layout.createSequentialGroup()
                                        .addGroup(panel16Layout.createParallelGroup()
                                            .addComponent(label11, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label10, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label9, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel16Layout.createParallelGroup()
                                            .addComponent(txtProductAddSalePrice, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                            .addComponent(txtProductAddStockQua, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                            .addComponent(txtProductAddPurcPrice, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel16Layout.createSequentialGroup()
                                        .addGap(0, 151, Short.MAX_VALUE)
                                        .addComponent(btnProductAdd, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                    );
                    panel16Layout.setVerticalGroup(
                        panel16Layout.createParallelGroup()
                            .addGroup(panel16Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(panel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductAddName, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(panel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label12, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbPrpductAdd, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel16Layout.createParallelGroup()
                                    .addComponent(label11, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductAddPurcPrice, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(panel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label10, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductAddStockQua, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(panel16Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label9, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductAddSalePrice, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbllAddError, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnProductAdd, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                }

                //======== panel17 ========
                {
                    panel17.setBorder(new TitledBorder(new LineBorder(new Color(0, 46, 195), 2), "Edit Product", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 14), Color.darkGray));
                    panel17.setBackground(Color.lightGray);

                    //---- label14 ----
                    label14.setText("Sale Price");
                    label14.setFont(label14.getFont().deriveFont(label14.getFont().getStyle() | Font.BOLD, label14.getFont().getSize() + 2f));
                    label14.setForeground(Color.darkGray);

                    //---- txtProductUpdateSalePrice ----
                    txtProductUpdateSalePrice.setFont(txtProductUpdateSalePrice.getFont().deriveFont(txtProductUpdateSalePrice.getFont().getSize() + 2f));

                    //---- label15 ----
                    label15.setText("Stock Quantity");
                    label15.setFont(label15.getFont().deriveFont(label15.getFont().getStyle() | Font.BOLD, label15.getFont().getSize() + 2f));
                    label15.setForeground(Color.darkGray);

                    //---- txtProductUpdateStockQua ----
                    txtProductUpdateStockQua.setFont(txtProductUpdateStockQua.getFont().deriveFont(txtProductUpdateStockQua.getFont().getSize() + 2f));

                    //---- label16 ----
                    label16.setText("Purchase Price");
                    label16.setFont(label16.getFont().deriveFont(label16.getFont().getStyle() | Font.BOLD, label16.getFont().getSize() + 2f));
                    label16.setForeground(Color.darkGray);

                    //---- txtProductUpdatePurcPrice ----
                    txtProductUpdatePurcPrice.setFont(txtProductUpdatePurcPrice.getFont().deriveFont(txtProductUpdatePurcPrice.getFont().getSize() + 2f));

                    //---- label17 ----
                    label17.setText("Category Name");
                    label17.setFont(label17.getFont().deriveFont(label17.getFont().getStyle() | Font.BOLD, label17.getFont().getSize() + 2f));
                    label17.setForeground(Color.darkGray);

                    //---- cmbProductUpdate ----
                    cmbProductUpdate.setFont(cmbProductUpdate.getFont().deriveFont(cmbProductUpdate.getFont().getSize() + 2f));
                    cmbProductUpdate.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            cmbProductUpdateMouseClicked(e);
                        }
                    });

                    //---- txtProductUpdateName ----
                    txtProductUpdateName.setFont(txtProductUpdateName.getFont().deriveFont(txtProductUpdateName.getFont().getSize() + 2f));

                    //---- label18 ----
                    label18.setText("Product Name");
                    label18.setFont(label18.getFont().deriveFont(label18.getFont().getStyle() | Font.BOLD, label18.getFont().getSize() + 2f));
                    label18.setForeground(Color.darkGray);

                    //---- btnProductUpdate ----
                    btnProductUpdate.setText("Update");
                    btnProductUpdate.setBackground(new Color(255, 154, 47));
                    btnProductUpdate.setFont(btnProductUpdate.getFont().deriveFont(btnProductUpdate.getFont().getStyle() | Font.BOLD, btnProductUpdate.getFont().getSize() + 6f));
                    btnProductUpdate.setForeground(Color.white);
                    btnProductUpdate.addActionListener(e -> btnProductUpdateClick(e));

                    //---- lblUpdateError ----
                    lblUpdateError.setText("text");
                    lblUpdateError.setFont(lblUpdateError.getFont().deriveFont(lblUpdateError.getFont().getStyle() | Font.BOLD, lblUpdateError.getFont().getSize() + 1f));
                    lblUpdateError.setForeground(new Color(204, 0, 0));

                    //---- productDelete ----
                    productDelete.setText("Delete");
                    productDelete.setBackground(new Color(255, 154, 47));
                    productDelete.setFont(productDelete.getFont().deriveFont(productDelete.getFont().getStyle() | Font.BOLD, productDelete.getFont().getSize() + 6f));
                    productDelete.setForeground(Color.white);
                    productDelete.addActionListener(e -> productDeleteClick(e));

                    GroupLayout panel17Layout = new GroupLayout(panel17);
                    panel17.setLayout(panel17Layout);
                    panel17Layout.setHorizontalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel17Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel17Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(GroupLayout.Alignment.LEADING, panel17Layout.createSequentialGroup()
                                            .addGroup(panel17Layout.createParallelGroup()
                                                .addComponent(label14, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label15))
                                            .addGap(52, 52, 52)
                                            .addGroup(panel17Layout.createParallelGroup()
                                                .addComponent(txtProductUpdateStockQua)
                                                .addComponent(txtProductUpdateSalePrice)))
                                        .addGroup(GroupLayout.Alignment.LEADING, panel17Layout.createSequentialGroup()
                                            .addGroup(panel17Layout.createParallelGroup()
                                                .addComponent(label18)
                                                .addComponent(label17, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label16, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
                                            .addGap(38, 38, 38)
                                            .addGroup(panel17Layout.createParallelGroup()
                                                .addGroup(panel17Layout.createSequentialGroup()
                                                    .addComponent(cmbProductUpdate, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(txtProductUpdatePurcPrice)
                                                .addComponent(txtProductUpdateName)))
                                        .addGroup(panel17Layout.createSequentialGroup()
                                            .addComponent(productDelete, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnProductUpdate, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblUpdateError, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(14, Short.MAX_VALUE))
                    );
                    panel17Layout.setVerticalGroup(
                        panel17Layout.createParallelGroup()
                            .addGroup(panel17Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(panel17Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label18, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductUpdateName, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel17Layout.createParallelGroup()
                                    .addComponent(label17, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbProductUpdate, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel17Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label16, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductUpdatePurcPrice, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel17Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label15, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductUpdateStockQua, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel17Layout.createParallelGroup()
                                    .addGroup(panel17Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                        .addComponent(label14, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(panel17Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtProductUpdateSalePrice, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)))
                                .addComponent(lblUpdateError)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel17Layout.createParallelGroup()
                                    .addComponent(btnProductUpdate, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(productDelete, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                    );
                }

                GroupLayout panel4Layout = new GroupLayout(panel4);
                panel4.setLayout(panel4Layout);
                panel4Layout.setHorizontalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(panel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(panel4Layout.createSequentialGroup()
                                    .addComponent(panel16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(panel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(9, Short.MAX_VALUE))
                );
                panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                        .addGroup(panel4Layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addComponent(panel14, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel4Layout.createParallelGroup()
                                .addComponent(panel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(panel16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap(44, Short.MAX_VALUE))
                );
            }
            tabPaneCustomer.addTab("Product Manegment", panel4);

            //======== panel3 ========
            {
                panel3.setBackground(Color.lightGray);

                //---- btnCompleteSale ----
                btnCompleteSale.setText("COMPLETE SALE");
                btnCompleteSale.setForeground(Color.white);
                btnCompleteSale.setFont(btnCompleteSale.getFont().deriveFont(btnCompleteSale.getFont().getStyle() | Font.BOLD, btnCompleteSale.getFont().getSize() + 6f));
                btnCompleteSale.setBackground(new Color(255, 154, 47));
                btnCompleteSale.addActionListener(e -> btnCompleteSaleClick(e));

                //---- lbl ----
                lbl.setText("TOTAL :");
                lbl.setFont(lbl.getFont().deriveFont(lbl.getFont().getStyle() | Font.BOLD, lbl.getFont().getSize() + 8f));
                lbl.setForeground(new Color(0, 71, 151));

                //---- lblSaleTotal ----
                lblSaleTotal.setForeground(new Color(0, 71, 151));
                lblSaleTotal.setFont(lblSaleTotal.getFont().deriveFont(lblSaleTotal.getFont().getStyle() | Font.BOLD, lblSaleTotal.getFont().getSize() + 8f));
                lblSaleTotal.setHorizontalAlignment(SwingConstants.RIGHT);
                lblSaleTotal.setBackground(new Color(0, 71, 151));
                lblSaleTotal.setIcon(null);
                lblSaleTotal.setText("0.00");

                //---- lblSaleError ----
                lblSaleError.setText("text");
                lblSaleError.setFont(lblSaleError.getFont().deriveFont(lblSaleError.getFont().getStyle() | Font.BOLD, lblSaleError.getFont().getSize() + 2f));
                lblSaleError.setForeground(new Color(204, 0, 0));
                lblSaleError.setHorizontalAlignment(SwingConstants.CENTER);

                //======== panel8 ========
                {
                    panel8.setBorder(new TitledBorder(new LineBorder(new Color(0, 75, 168), 2), "Customer Basket", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16), Color.darkGray));
                    panel8.setBackground(Color.lightGray);

                    //======== scrollPane2 ========
                    {

                        //---- tblBasket ----
                        tblBasket.setFont(tblBasket.getFont().deriveFont(tblBasket.getFont().getSize() + 2f));
                        tblBasket.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        scrollPane2.setViewportView(tblBasket);
                    }

                    GroupLayout panel8Layout = new GroupLayout(panel8);
                    panel8.setLayout(panel8Layout);
                    panel8Layout.setHorizontalGroup(
                        panel8Layout.createParallelGroup()
                            .addGroup(panel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
                    );
                    panel8Layout.setVerticalGroup(
                        panel8Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel8Layout.createSequentialGroup()
                                .addContainerGap(14, Short.MAX_VALUE)
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                }

                //---- btnAddBucket ----
                btnAddBucket.setForeground(new Color(0, 71, 151));
                btnAddBucket.setFont(btnAddBucket.getFont().deriveFont(btnAddBucket.getFont().getStyle() | Font.BOLD, btnAddBucket.getFont().getSize() + 5f));
                btnAddBucket.setBackground(Color.lightGray);
                btnAddBucket.setIcon(new ImageIcon(getClass().getResource("/add-to-cart.png")));
                btnAddBucket.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.lightGray, Color.gray, Color.gray, Color.lightGray));
                btnAddBucket.setText("Add to ");
                btnAddBucket.addActionListener(e -> btnAddBucketClick(e));

                //---- btnDeleteBucket ----
                btnDeleteBucket.setForeground(new Color(0, 71, 151));
                btnDeleteBucket.setFont(btnDeleteBucket.getFont().deriveFont(Font.BOLD, btnDeleteBucket.getFont().getSize() + 5f));
                btnDeleteBucket.setBackground(Color.lightGray);
                btnDeleteBucket.setIcon(new ImageIcon(getClass().getResource("/remove-from-cart (1).png")));
                btnDeleteBucket.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.lightGray, Color.gray, Color.gray, Color.lightGray));
                btnDeleteBucket.setText("Remove from");
                btnDeleteBucket.addActionListener(e -> btnDeleteBucket(e));

                //---- label8 ----
                label8.setText("Quantity :");
                label8.setFont(label8.getFont().deriveFont(label8.getFont().getSize() + 3f));
                label8.setForeground(Color.darkGray);

                //======== panel12 ========
                {
                    panel12.setBorder(new TitledBorder(new LineBorder(new Color(0, 69, 176), 2), "Customer Selection", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16), Color.darkGray));
                    panel12.setBackground(Color.lightGray);

                    //======== scrollPane5 ========
                    {

                        //---- tblCustomerSale ----
                        tblCustomerSale.setFont(tblCustomerSale.getFont().deriveFont(tblCustomerSale.getFont().getSize() + 2f));
                        tblCustomerSale.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        tblCustomerSale.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                tblCustomerSaleMouseReleased(e);
                            }
                        });
                        scrollPane5.setViewportView(tblCustomerSale);
                    }

                    //---- label7 ----
                    label7.setText("Search for customer name :");
                    label7.setFont(label7.getFont().deriveFont(label7.getFont().getSize() + 2f));
                    label7.setForeground(Color.darkGray);

                    //---- txtSearchCustomer ----
                    txtSearchCustomer.setFont(txtSearchCustomer.getFont().deriveFont(txtSearchCustomer.getFont().getSize() + 2f));
                    txtSearchCustomer.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtSearchCustomerKeyReleased(e);
                        }
                    });

                    GroupLayout panel12Layout = new GroupLayout(panel12);
                    panel12.setLayout(panel12Layout);
                    panel12Layout.setHorizontalGroup(
                        panel12Layout.createParallelGroup()
                            .addGroup(panel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel12Layout.createParallelGroup()
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addComponent(label7, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSearchCustomer, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 9, Short.MAX_VALUE))
                                    .addGroup(panel12Layout.createSequentialGroup()
                                        .addComponent(scrollPane5, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                        .addContainerGap())))
                    );
                    panel12Layout.setVerticalGroup(
                        panel12Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel12Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panel12Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSearchCustomer, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                    );
                }

                //---- spinnerquantity ----
                spinnerquantity.setFont(spinnerquantity.getFont().deriveFont(spinnerquantity.getFont().getSize() + 2f));

                //---- label5 ----
                label5.setText("Select a category:");
                label5.setFont(label5.getFont().deriveFont(label5.getFont().getSize() + 3f));
                label5.setForeground(Color.darkGray);

                //---- cmbcategory2 ----
                cmbcategory2.setFont(cmbcategory2.getFont().deriveFont(cmbcategory2.getFont().getSize() + 2f));
                cmbcategory2.addActionListener(e -> cmbcategory2Click(e));

                //======== panel11 ========
                {
                    panel11.setBorder(new TitledBorder(new LineBorder(new Color(0, 74, 177), 2), "Product Selection", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16), Color.darkGray));
                    panel11.setBackground(Color.lightGray);

                    //======== scrollPane4 ========
                    {

                        //---- tblproductSale ----
                        tblproductSale.setFont(tblproductSale.getFont().deriveFont(tblproductSale.getFont().getSize() + 2f));
                        tblproductSale.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        tblproductSale.setEnabled(false);
                        tblproductSale.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                tblproductSaleMouseReleased(e);
                            }
                        });
                        scrollPane4.setViewportView(tblproductSale);
                    }

                    //---- label6 ----
                    label6.setText("Search for product  name:");
                    label6.setFont(label6.getFont().deriveFont(label6.getFont().getSize() + 2f));
                    label6.setForeground(Color.darkGray);

                    //---- txtSearchProduct3 ----
                    txtSearchProduct3.setFont(txtSearchProduct3.getFont().deriveFont(txtSearchProduct3.getFont().getSize() + 2f));
                    txtSearchProduct3.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtSearchProduct3KeyReleased(e);
                        }
                    });

                    GroupLayout panel11Layout = new GroupLayout(panel11);
                    panel11.setLayout(panel11Layout);
                    panel11Layout.setHorizontalGroup(
                        panel11Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel11Layout.createSequentialGroup()
                                .addContainerGap(14, Short.MAX_VALUE)
                                .addGroup(panel11Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panel11Layout.createSequentialGroup()
                                        .addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtSearchProduct3, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14))
                    );
                    panel11Layout.setVerticalGroup(
                        panel11Layout.createParallelGroup()
                            .addGroup(panel11Layout.createSequentialGroup()
                                .addGroup(panel11Layout.createParallelGroup()
                                    .addComponent(txtSearchProduct3, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(19, Short.MAX_VALUE))
                    );
                }

                //---- label29 ----
                label29.setText("Date :");
                label29.setFont(label29.getFont().deriveFont(label29.getFont().getSize() + 3f));
                label29.setForeground(Color.darkGray);

                //---- btnCancelSale ----
                btnCancelSale.setText("CANCEL");
                btnCancelSale.setForeground(Color.white);
                btnCancelSale.setFont(btnCancelSale.getFont().deriveFont(btnCancelSale.getFont().getStyle() | Font.BOLD, btnCancelSale.getFont().getSize() + 6f));
                btnCancelSale.setBackground(new Color(255, 154, 47));
                btnCancelSale.addActionListener(e -> btnCancelSaleClick(e));

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addComponent(lblSaleError, GroupLayout.PREFERRED_SIZE, 873, GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                    .addGroup(panel3Layout.createParallelGroup()
                                        .addComponent(panel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addGap(12, 12, 12)
                                    .addGroup(panel3Layout.createParallelGroup()
                                        .addGroup(panel3Layout.createSequentialGroup()
                                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbcategory2, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
                                            .addGap(14, 14, 14))
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(panel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                            .addGroup(panel3Layout.createParallelGroup()
                                                .addGroup(panel3Layout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addGroup(panel3Layout.createParallelGroup()
                                                        .addGroup(panel3Layout.createSequentialGroup()
                                                            .addComponent(label8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addGap(18, 18, 18)
                                                            .addComponent(spinnerquantity, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(btnAddBucket, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(32, 32, 32)
                                                    .addGroup(panel3Layout.createParallelGroup()
                                                        .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                                            .addComponent(label29, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(txtDateSale, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
                                                            .addGap(2, 2, 2))
                                                        .addComponent(btnDeleteBucket, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(panel3Layout.createSequentialGroup()
                                                            .addComponent(btnCompleteSale, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(btnCancelSale, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(panel3Layout.createSequentialGroup()
                                                            .addGap(0, 0, Short.MAX_VALUE)
                                                            .addComponent(lbl, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                            .addGap(18, 18, 18)
                                                            .addComponent(lblSaleTotal, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
                                                    .addGap(11, 11, 11)))
                                            .addGap(10, 10, 10)))
                                    .addGap(12, 12, 12))))
                );
                panel3Layout.setVerticalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addGroup(panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmbcategory2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(panel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addComponent(panel12, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label8, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(spinnerquantity, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label29, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                        .addComponent(txtDateSale, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                                    .addGap(43, 43, 43)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnDeleteBucket, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddBucket))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbl, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSaleTotal, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
                                    .addGap(34, 34, 34)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCompleteSale, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCancelSale, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                    .addComponent(panel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addComponent(lblSaleError, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25))
                );
            }
            tabPaneCustomer.addTab("Sale Manegment", panel3);

            //======== panel1 ========
            {
                panel1.setBackground(Color.lightGray);

                //======== panel19 ========
                {
                    panel19.setBorder(new TitledBorder(new LineBorder(new Color(0, 48, 237), 2), "General profit-loss situation", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 15), Color.darkGray));
                    panel19.setBackground(Color.lightGray);

                    //---- label1 ----
                    label1.setText("Profit-loss situation");
                    label1.setFont(label1.getFont().deriveFont(label1.getFont().getStyle() | Font.BOLD, label1.getFont().getSize() + 3f));
                    label1.setForeground(Color.darkGray);

                    //---- lblRepotsProfit ----
                    lblRepotsProfit.setText("text");
                    lblRepotsProfit.setFont(lblRepotsProfit.getFont().deriveFont(lblRepotsProfit.getFont().getStyle() | Font.BOLD, lblRepotsProfit.getFont().getSize() + 4f));
                    lblRepotsProfit.setForeground(new Color(0, 35, 210));
                    lblRepotsProfit.setHorizontalAlignment(SwingConstants.RIGHT);

                    GroupLayout panel19Layout = new GroupLayout(panel19);
                    panel19.setLayout(panel19Layout);
                    panel19Layout.setHorizontalGroup(
                        panel19Layout.createParallelGroup()
                            .addGroup(panel19Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(lblRepotsProfit, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                    panel19Layout.setVerticalGroup(
                        panel19Layout.createParallelGroup()
                            .addGroup(panel19Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(panel19Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRepotsProfit, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(22, Short.MAX_VALUE))
                    );
                }

                //======== panel13 ========
                {
                    panel13.setBorder(new TitledBorder(new LineBorder(new Color(43, 62, 227), 2), "Result Table", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 15), Color.darkGray));
                    panel13.setBackground(Color.lightGray);

                    //======== scrollPane6 ========
                    {
                        scrollPane6.setViewportView(tblReport);
                    }

                    GroupLayout panel13Layout = new GroupLayout(panel13);
                    panel13.setLayout(panel13Layout);
                    panel13Layout.setHorizontalGroup(
                        panel13Layout.createParallelGroup()
                            .addGroup(panel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane6)
                                .addGap(14, 14, 14))
                    );
                    panel13Layout.setVerticalGroup(
                        panel13Layout.createParallelGroup()
                            .addGroup(panel13Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(17, Short.MAX_VALUE))
                    );
                }

                //======== panel22 ========
                {
                    panel22.setBackground(Color.lightGray);
                    panel22.setBorder(new TitledBorder(new LineBorder(new Color(54, 54, 224), 2), "Filter", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 15), Color.darkGray));

                    //---- rdbCustomer ----
                    rdbCustomer.setText("Customer");
                    rdbCustomer.setFont(rdbCustomer.getFont().deriveFont(rdbCustomer.getFont().getStyle() | Font.BOLD, rdbCustomer.getFont().getSize() + 4f));
                    rdbCustomer.setForeground(Color.white);
                    rdbCustomer.setBackground(new Color(255, 154, 47));

                    //---- rrdbProduct ----
                    rrdbProduct.setText("Product Name");
                    rrdbProduct.setFont(rrdbProduct.getFont().deriveFont(rrdbProduct.getFont().getStyle() | Font.BOLD, rrdbProduct.getFont().getSize() + 4f));
                    rrdbProduct.setForeground(Color.white);
                    rrdbProduct.setBackground(new Color(255, 154, 47));

                    //---- rdbCategory ----
                    rdbCategory.setText("Category Name");
                    rdbCategory.setFont(rdbCategory.getFont().deriveFont(rdbCategory.getFont().getStyle() | Font.BOLD, rdbCategory.getFont().getSize() + 4f));
                    rdbCategory.setForeground(Color.white);
                    rdbCategory.setBackground(new Color(255, 154, 47));

                    //---- txtReportSearch ----
                    txtReportSearch.setFont(txtReportSearch.getFont().deriveFont(txtReportSearch.getFont().getSize() + 2f));
                    txtReportSearch.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            txtReportSearchKeyReleased(e);
                        }
                    });

                    //---- datePicker2 ----
                    datePicker2.setBackground(new Color(255, 154, 47));
                    datePicker2.addPropertyChangeListener(e -> datePicker2PropertyChange(e));

                    //---- datePicker1 ----
                    datePicker1.setBackground(new Color(255, 154, 47));
                    datePicker1.setFont(datePicker1.getFont().deriveFont(datePicker1.getFont().getSize() + 1f));
                    datePicker1.addPropertyChangeListener(e -> datePicker1PropertyChange(e));

                    //---- label2 ----
                    label2.setText("Search For:");
                    label2.setFont(label2.getFont().deriveFont(label2.getFont().getStyle() | Font.BOLD, label2.getFont().getSize() + 3f));
                    label2.setForeground(Color.darkGray);

                    //---- label30 ----
                    label30.setText("Search :");
                    label30.setFont(label30.getFont().deriveFont(label30.getFont().getStyle() | Font.BOLD, label30.getFont().getSize() + 3f));
                    label30.setForeground(Color.darkGray);

                    //---- label31 ----
                    label31.setText("Date From :");
                    label31.setFont(label31.getFont().deriveFont(label31.getFont().getStyle() | Font.BOLD, label31.getFont().getSize() + 3f));
                    label31.setForeground(Color.darkGray);

                    //---- label32 ----
                    label32.setText("to :");
                    label32.setFont(label32.getFont().deriveFont(label32.getFont().getStyle() | Font.BOLD, label32.getFont().getSize() + 2f));
                    label32.setForeground(Color.darkGray);

                    //---- lblReportError ----
                    lblReportError.setHorizontalAlignment(SwingConstants.CENTER);
                    lblReportError.setFont(lblReportError.getFont().deriveFont(lblReportError.getFont().getStyle() | Font.BOLD, lblReportError.getFont().getSize() + 1f));
                    lblReportError.setForeground(new Color(210, 15, 15));

                    GroupLayout panel22Layout = new GroupLayout(panel22);
                    panel22.setLayout(panel22Layout);
                    panel22Layout.setHorizontalGroup(
                        panel22Layout.createParallelGroup()
                            .addGroup(panel22Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel22Layout.createParallelGroup()
                                    .addGroup(panel22Layout.createSequentialGroup()
                                        .addGroup(panel22Layout.createParallelGroup()
                                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label30, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label31, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panel22Layout.createParallelGroup()
                                            .addGroup(panel22Layout.createSequentialGroup()
                                                .addComponent(rdbCustomer, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                                                .addComponent(rrdbProduct, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
                                                .addGap(82, 82, 82)
                                                .addComponent(rdbCategory, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtReportSearch, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                                            .addGroup(panel22Layout.createSequentialGroup()
                                                .addComponent(datePicker1, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
                                                .addGap(95, 95, 95)
                                                .addComponent(label32, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                                .addComponent(datePicker2, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(lblReportError, GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE))
                                .addGap(14, 14, 14))
                    );
                    panel22Layout.setVerticalGroup(
                        panel22Layout.createParallelGroup()
                            .addGroup(panel22Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(panel22Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label2)
                                    .addComponent(rdbCategory, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdbCustomer, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rrdbProduct, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(panel22Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label30)
                                    .addComponent(txtReportSearch, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel22Layout.createParallelGroup()
                                    .addGroup(panel22Layout.createSequentialGroup()
                                        .addGroup(panel22Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label31, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(datePicker1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
                                        .addGap(12, 12, 12))
                                    .addGroup(panel22Layout.createSequentialGroup()
                                        .addGroup(panel22Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(datePicker2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label32, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(lblReportError, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                }

                //---- btnPdfCreateReport ----
                btnPdfCreateReport.setText(" ");
                btnPdfCreateReport.setFont(btnPdfCreateReport.getFont().deriveFont(btnPdfCreateReport.getFont().getStyle() | Font.BOLD, btnPdfCreateReport.getFont().getSize() + 6f));
                btnPdfCreateReport.setForeground(new Color(0, 71, 151));
                btnPdfCreateReport.setBackground(Color.lightGray);
                btnPdfCreateReport.setIcon(new ImageIcon(getClass().getResource("/pdf.png")));
                btnPdfCreateReport.addActionListener(e -> btnPdfCreateReportClick(e));

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addComponent(panel19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 441, Short.MAX_VALUE)
                                    .addComponent(btnPdfCreateReport)
                                    .addGap(12, 12, 12))
                                .addComponent(panel13, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(panel22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(panel13, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(panel1Layout.createParallelGroup()
                                .addComponent(btnPdfCreateReport, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                .addComponent(panel19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(35, 35, 35))
                );
            }
            tabPaneCustomer.addTab("Reports", panel1);

            //======== panel6 ========
            {
                panel6.setBackground(Color.lightGray);

                //======== panel23 ========
                {
                    panel23.setBorder(new LineBorder(new Color(0, 43, 210), 2));

                    //---- label33 ----
                    label33.setText("Password");
                    label33.setFont(label33.getFont().deriveFont(label33.getFont().getStyle() | Font.BOLD, label33.getFont().getSize() + 3f));
                    label33.setForeground(Color.darkGray);

                    //---- txtOldPassword ----
                    txtOldPassword.setFont(txtOldPassword.getFont().deriveFont(txtOldPassword.getFont().getSize() + 2f));

                    //---- txtNewPasswordAgain ----
                    txtNewPasswordAgain.setFont(txtNewPasswordAgain.getFont().deriveFont(txtNewPasswordAgain.getFont().getSize() + 2f));

                    //---- label34 ----
                    label34.setText("New Password Again");
                    label34.setFont(label34.getFont().deriveFont(label34.getFont().getStyle() | Font.BOLD, label34.getFont().getSize() + 3f));
                    label34.setForeground(Color.darkGray);

                    //---- txtNewPassword ----
                    txtNewPassword.setFont(txtNewPassword.getFont().deriveFont(txtNewPassword.getFont().getSize() + 2f));

                    //---- label35 ----
                    label35.setText("New Password");
                    label35.setFont(label35.getFont().deriveFont(label35.getFont().getStyle() | Font.BOLD, label35.getFont().getSize() + 3f));
                    label35.setForeground(Color.darkGray);

                    //---- btnChangePassword ----
                    btnChangePassword.setText("Approve");
                    btnChangePassword.setFont(btnChangePassword.getFont().deriveFont(btnChangePassword.getFont().getStyle() | Font.BOLD, btnChangePassword.getFont().getSize() + 5f));
                    btnChangePassword.setForeground(Color.white);
                    btnChangePassword.setBackground(new Color(255, 154, 47));
                    btnChangePassword.addActionListener(e -> btnChangePasswordClick(e));

                    //---- lblUserSettingsError ----
                    lblUserSettingsError.setHorizontalAlignment(SwingConstants.CENTER);
                    lblUserSettingsError.setForeground(new Color(220, 0, 0));

                    GroupLayout panel23Layout = new GroupLayout(panel23);
                    panel23.setLayout(panel23Layout);
                    panel23Layout.setHorizontalGroup(
                        panel23Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel23Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblUserSettingsError, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79))
                            .addGroup(panel23Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panel23Layout.createSequentialGroup()
                                        .addComponent(label33, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                        .addComponent(txtOldPassword, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel23Layout.createSequentialGroup()
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(label35, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                            .addComponent(label34, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                        .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNewPasswordAgain, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                            .addComponent(txtNewPassword, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                            .addComponent(btnChangePassword, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))))
                                .addContainerGap(37, Short.MAX_VALUE))
                    );
                    panel23Layout.setVerticalGroup(
                        panel23Layout.createParallelGroup()
                            .addGroup(panel23Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label33, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtOldPassword, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNewPassword, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label35, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(panel23Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label34, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNewPasswordAgain, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                .addGap(48, 48, 48)
                                .addComponent(btnChangePassword, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(lblUserSettingsError, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(22, Short.MAX_VALUE))
                    );
                }

                //---- label36 ----
                label36.setText("Change");
                label36.setForeground(new Color(255, 149, 31));
                label36.setFont(new Font("Segoe UI", Font.BOLD, 28));

                //---- label37 ----
                label37.setText("Password");
                label37.setForeground(new Color(61, 77, 214));
                label37.setFont(new Font("Segoe UI", Font.BOLD, 28));

                GroupLayout panel6Layout = new GroupLayout(panel6);
                panel6.setLayout(panel6Layout);
                panel6Layout.setHorizontalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addContainerGap(231, Short.MAX_VALUE)
                            .addGroup(panel6Layout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                    .addComponent(label36)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label37)
                                    .addGap(336, 336, 336))
                                .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                    .addComponent(panel23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(219, 219, 219))))
                );
                panel6Layout.setVerticalGroup(
                    panel6Layout.createParallelGroup()
                        .addGroup(panel6Layout.createSequentialGroup()
                            .addGap(41, 41, 41)
                            .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label37, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label36))
                            .addGap(23, 23, 23)
                            .addComponent(panel23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(88, Short.MAX_VALUE))
                );
            }
            tabPaneCustomer.addTab("User Settings", panel6);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabPaneCustomer, GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(tabPaneCustomer, GroupLayout.PREFERRED_SIZE, 676, GroupLayout.PREFERRED_SIZE))
        );
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rdbCustomer);
        buttonGroup1.add(rrdbProduct);
        buttonGroup1.add(rdbCategory);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabPaneCustomer;
    private JPanel panel2;
    private JPanel panel18;
    private JScrollPane scrollPane8;
    private JTable tblCustomer;
    private JPanel panel20;
    private JLabel label19;
    private JLabel label20;
    private JLabel label21;
    private JLabel label22;
    private JTextField txtCustomerAddName;
    private JTextField txtCustomerAddSurname;
    private JTextField txtCustomerAddPhone;
    private JTextField txtCustomerAddAddress;
    private JButton btnNewCustomerAdd;
    private JTextField txtCustomerAddEmail;
    private JLabel label27;
    private JLabel lblCustomerError;
    private JPanel panel21;
    private JLabel label23;
    private JLabel label24;
    private JLabel label25;
    private JLabel label26;
    private JTextField txtCustomerEditName;
    private JTextField txtCustomerEditSurname;
    private JTextField txtCustomerEditPhone;
    private JTextField txtCustomerEditAdress;
    private JButton btnCustomerUpdate;
    private JButton btnCustomerDelete;
    private JTextField txtCustomerEditEmail;
    private JLabel label28;
    private JLabel lblCustomerError2;
    private JPanel panel5;
    private JPanel panel7;
    private JScrollPane scrollPane1;
    private JTable tblCategory;
    private JPanel panel9;
    private JLabel label3;
    private JTextField txtCategoryName;
    private JLabel label4;
    private JScrollPane scrollPane3;
    private JTextArea txtCategoryDescription;
    private JButton btnCategoryAdd;
    private JButton btnCategoryDelete;
    private JButton btnCategoryUpdate;
    private JLabel lblError;
    private JPanel panel4;
    private JPanel panel14;
    private JScrollPane scrollPane7;
    private JTable tblProduct;
    private JPanel panel15;
    private JPanel panel16;
    private JLabel label9;
    private JTextField txtProductAddSalePrice;
    private JButton btnProductAdd;
    private JTextField txtProductAddStockQua;
    private JLabel label10;
    private JLabel label11;
    private JTextField txtProductAddPurcPrice;
    private JLabel label12;
    private JComboBox cmbPrpductAdd;
    private JLabel label13;
    private JTextField txtProductAddName;
    private JLabel lbllAddError;
    private JPanel panel17;
    private JLabel label14;
    private JTextField txtProductUpdateSalePrice;
    private JLabel label15;
    private JTextField txtProductUpdateStockQua;
    private JLabel label16;
    private JTextField txtProductUpdatePurcPrice;
    private JLabel label17;
    private JComboBox cmbProductUpdate;
    private JTextField txtProductUpdateName;
    private JLabel label18;
    private JButton btnProductUpdate;
    private JLabel lblUpdateError;
    private JButton productDelete;
    private JPanel panel3;
    private JButton btnCompleteSale;
    private JLabel lbl;
    private JLabel lblSaleTotal;
    private JLabel lblSaleError;
    private JPanel panel8;
    private JScrollPane scrollPane2;
    private JTable tblBasket;
    private JButton btnAddBucket;
    private JButton btnDeleteBucket;
    private JLabel label8;
    private JPanel panel12;
    private JScrollPane scrollPane5;
    private JTable tblCustomerSale;
    private JLabel label7;
    private JTextField txtSearchCustomer;
    private JSpinner spinnerquantity;
    private JLabel label5;
    private JComboBox cmbcategory2;
    private JPanel panel11;
    private JScrollPane scrollPane4;
    private JTable tblproductSale;
    private JLabel label6;
    private JTextField txtSearchProduct3;
    private JLabel label29;
    private JTextField txtDateSale;
    private JButton btnCancelSale;
    private JPanel panel1;
    private JPanel panel19;
    private JLabel label1;
    private JLabel lblRepotsProfit;
    private JPanel panel13;
    private JScrollPane scrollPane6;
    private JTable tblReport;
    private JPanel panel22;
    private JRadioButton rdbCustomer;
    private JRadioButton rrdbProduct;
    private JRadioButton rdbCategory;
    private JTextField txtReportSearch;
    private DatePicker datePicker2;
    private DatePicker datePicker1;
    private JLabel label2;
    private JLabel label30;
    private JLabel label31;
    private JLabel label32;
    private JLabel lblReportError;
    private JButton btnPdfCreateReport;
    private JPanel panel6;
    private JPanel panel23;
    private JLabel label33;
    private JPasswordField txtOldPassword;
    private JPasswordField txtNewPasswordAgain;
    private JLabel label34;
    private JPasswordField txtNewPassword;
    private JLabel label35;
    private JButton btnChangePassword;
    private JLabel lblUserSettingsError;
    private JLabel label36;
    private JLabel label37;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
