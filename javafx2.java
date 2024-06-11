package org.example.lr18;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FoodOrder {
    private final SimpleIntegerProperty orderId;
    private final SimpleStringProperty foodItem;
    private final SimpleStringProperty customerName;

    public FoodOrder(int orderId, String foodItem, String customerName) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.foodItem = new SimpleStringProperty(foodItem);
        this.customerName = new SimpleStringProperty(customerName);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public String getFoodItem() {
        return foodItem.get();
    }

    public SimpleStringProperty foodItemProperty() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem.set(foodItem);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }
}
Лістинг коду: 
package org.example.lr18;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.input.MouseButton;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableRow;

public class FoodOrderTable extends TableView<FoodOrder> {
    private int nextOrderId = 1; // Лічильник для генерації унікальних ідентифікаторів

    public FoodOrderTable() {
        // Створюємо стовпці таблиці
        TableColumn<FoodOrder, Integer> orderIdCol = new TableColumn<>("ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        TableColumn<FoodOrder, String> foodItemCol = new TableColumn<>("Страва");
        foodItemCol.setCellValueFactory(new PropertyValueFactory<>("foodItem"));
        foodItemCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<FoodOrder, String> customerNameCol = new TableColumn<>("Клієнт");
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        getColumns().addAll(orderIdCol, foodItemCol, customerNameCol);
        setEditable(true); // Робимо таблицю редагованою



        // Додаємо можливість вибору рядка за допомогою правої кнопки миші
        setRowFactory(tableView -> {
            final TableRow<FoodOrder> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY) {
                    row.getContextMenu().show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    public void setOrders(ObservableList<FoodOrder> orders) {
        setItems(orders);
    }

    public void addOrder(FoodOrder order) {
        getItems().add(order);
    }

    public void removeOrder(FoodOrder order) {
        getItems().remove(order);
    }

    public ObservableList<FoodOrder> getOrders() {
        return getItems();
    }

    public void addEmptyOrder() {
        FoodOrder emptyOrder = new FoodOrder(nextOrderId++, "", "");
        addOrder(emptyOrder);
    }
}
Лістинг коду: 
package org.example.lr18;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.Button;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        FoodOrderTable orderTable = new FoodOrderTable();

        orderTable.addOrder(new FoodOrder(1, "Піца", "Петро"));
        orderTable.addOrder(new FoodOrder(2, "Суші", "Анна"));
        orderTable.addOrder(new FoodOrder(3, "Бургер", "Артем"));

        Button addButton = new Button("Додати порожнє замовлення");
        addButton.setOnAction(e -> {
            orderTable.addEmptyOrder();
        });

        Button deleteButton = new Button("Видалити замовлення");
        deleteButton.setOnAction(e -> {
            FoodOrder selectedItem = orderTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                orderTable.removeOrder(selectedItem);
            }
        });

        VBox root = new VBox(orderTable, addButton, deleteButton);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Food Delivery Orders");
        primaryStage.show();
    }


}
