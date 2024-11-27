package com.example.expensemanagement.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.expensemanagement.Entity.Expenses;
import com.example.expensemanagement.Interface.*;


import java.util.ArrayList;
import java.util.List;

public class ExpensesManager extends SQLiteOpenHelper {

    public ExpensesManager(@Nullable Context context) {
        super(context, "Db_project.db", null,34);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, " +
                "amount INTEGER, " +
                "category TEXT, " +
                "description TEXT," +
                "userName TEXT"+
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("drop table if exists expenses");
        onCreate(sqLiteDatabase);

    }
    // add new expenses
    public boolean addNew(Expenses obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Do not put "id" if it's AUTOINCREMENT
        values.put("date", obj.getDate());
        values.put("amount", obj.getAmount());
        values.put("category", obj.getCategory());
        values.put("description", obj.getDescription());
        values.put("userName", obj.getUserName());

        long result = -1;
        try {
            result = db.insert("expenses", null, values);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions
        } finally {
            db.close();
        }

        return result != -1; // Return true if insert succeeded, false otherwise
    }


    // update name chua xon
    public void updateExpense(int id, int amount, String description) {
        SQLiteDatabase db = this.getWritableDatabase(); // Use writable database for updates
        ContentValues value = new ContentValues();

        // Set the new values
        value.put("amount", amount);
        value.put("description", description);

        // Update the record where id matches
        int rowsUpdated = db.update(
                "expenses",          // Table name
                value,               // New values
                "id=?",              // WHERE clause
                new String[]{String.valueOf(id)} // WHERE arguments
        );
        db.close();
    }

    //show data
    public ArrayList<Expenses> getAll(String userName) {
        ArrayList<Expenses> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from expenses where userName = ?; ";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});

        if (cursor.moveToFirst()) {
            do{
                Expenses obj = new Expenses();
                obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                obj.setDate(cursor.getString(1));
                obj.setAmount(cursor.getInt(2));
                obj.setCategory(cursor.getString(3));
                obj.setDescription(cursor.getString(4));
                obj.setUserName(cursor.getString(5));
                arr.add(obj);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arr;
    }

    // getAll thu
    public ArrayList<Expenses> getAllThu(String userName) {
        ArrayList<Expenses> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM expenses WHERE category = 'Thu' AND userName = ?; ";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        if (cursor.moveToFirst()) { // Di chuyển đến hàng đầu tiên
            do {
                Expenses obj = new Expenses();
                obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                obj.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date"))); // Luôn nên sử dụng getColumnIndexOrThrow
                obj.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow("amount")));
                obj.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
                obj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                obj.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("userName")));
                arr.add(obj); // Thêm đối tượng vào danh sách
            } while (cursor.moveToNext()); // Di chuyển đến hàng tiếp theo
        }
        cursor.close(); // Đừng quên đóng cursor
        db.close(); // Đóng database
        return arr;
    }

    // get All Chi
    public ArrayList<Expenses> getAllChi(String userName) {
        ArrayList<Expenses> arr = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM expenses WHERE category = 'Chi' AND userName = ?; ";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});

        if (cursor.moveToFirst()) { // Bắt đầu từ hàng đầu tiên
            do {
                Expenses obj = new Expenses();
                obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id"))); // Đảm bảo tên cột chính xác
                obj.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                obj.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow("amount")));
                obj.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
                obj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                obj.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("userName")));
                arr.add(obj); // Thêm vào danh sách
            } while (cursor.moveToNext()); // Chuyển đến hàng tiếp theo
        }

        cursor.close(); // Đóng cursor
        db.close(); // Đóng database
        return arr;
    }

    // delete
    public boolean deleteExpenses(int expenses){
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("expenses", "id=?", new String[]{String.valueOf(expenses)});
        db.close();
        return rowsDeleted > 0;
    }

    // select sum where category Chi
    public int getTotalAmountForChi(String userName) {
        int totalAmount = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String sql = "SELECT SUM(amount) AS total FROM expenses WHERE category = 'Chi' AND userName = ?;";
            cursor = db.rawQuery(sql, new String[]{userName});
            if (cursor.moveToFirst()) {
                totalAmount = cursor.isNull(cursor.getColumnIndexOrThrow("total"))
                        ? 0 // Nếu NULL, trả về 0
                        : cursor.getInt(cursor.getColumnIndexOrThrow("total")); // Giá trị thực tế
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi nếu có
        } finally {
            if (cursor != null) cursor.close(); // Đảm bảo cursor được đóng
            if (db != null) db.close(); // Đảm bảo database được đóng
        }
        return totalAmount;
    }


    // select sum where category Thu
    public int getTotalAmountForThu(String userName) {
        int totalAmount = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String sql = "SELECT SUM(amount) AS total FROM expenses WHERE category = 'Thu' AND userName = ?;";
            cursor = db.rawQuery(sql, new String[]{userName});
            if (cursor.moveToFirst()) {
                totalAmount = cursor.isNull(cursor.getColumnIndexOrThrow("total"))
                        ? 0 // Nếu NULL, gán giá trị mặc định là 0
                        : cursor.getInt(cursor.getColumnIndexOrThrow("total")); // Lấy giá trị thực tế
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để kiểm tra
        } finally {
            if (cursor != null) cursor.close(); // Đảm bảo cursor được đóng
            if (db != null) db.close(); // Đảm bảo database được đóng
        }
        return totalAmount;
    }

    public List<MonthlySummary> getMonthlySummary(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MonthlySummary> summaryList = new ArrayList<>();

        String query = "SELECT " +
                "strftime('%m', date) AS month," +
                "SUM(CASE WHEN category = 'Thu' THEN amount ELSE 0 END) AS total_income," +
                "SUM(CASE WHEN category = 'Chi' THEN amount ELSE 0 END) AS total_expense , " +
                "SUM(amount) AS total " +
                "FROM expenses " +
                "WHERE userName = ?"+
                "GROUP BY month " +
                "ORDER BY month";

        Cursor cursor = db.rawQuery(query, new String[]{userName});

        if (cursor.moveToFirst()) {
            do {
                String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));
                Float totalIncome = cursor.getFloat(cursor.getColumnIndexOrThrow("total_income"));
                Float totalExpense = cursor.getFloat(cursor.getColumnIndexOrThrow("total_expense"));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
                MonthlySummary summary = new MonthlySummary(month, totalIncome, totalExpense);
                summaryList.add(summary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return summaryList;
    }
// find
public ArrayList<Expenses> listExpensesFind(String userName, String description) {
    ArrayList<Expenses> arr = new ArrayList<>();
    SQLiteDatabase db = null;
    Cursor cursor = null;

    try {
        db = this.getReadableDatabase();
        String sql = "SELECT * FROM expenses WHERE description LIKE ? AND userName = ?;";
        cursor = db.rawQuery(sql, new String[]{"%" + description + "%", userName});

        if (cursor.moveToFirst()) {
            do {
                Expenses obj = new Expenses();
                obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                obj.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                obj.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow("amount")));
                obj.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
                obj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                obj.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("userName")));
                arr.add(obj);
            } while (cursor.moveToNext());
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }

    return arr;
}

    public ArrayList<Expenses> listExpensesFindThu(String userName, String description) {
        ArrayList<Expenses> arr = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            String sql = "SELECT * FROM expenses WHERE description LIKE ? AND userName = ? AND category ='Thu';";
            cursor = db.rawQuery(sql, new String[]{"%" + description + "%", userName});

            if (cursor.moveToFirst()) {
                do {
                    Expenses obj = new Expenses();
                    obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    obj.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    obj.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow("amount")));
                    obj.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
                    obj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                    obj.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("userName")));
                    arr.add(obj);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return arr;
    }

    public ArrayList<Expenses> listExpensesFindChi(String userName, String description) {
        ArrayList<Expenses> arr = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            String sql = "SELECT * FROM expenses WHERE description LIKE ? AND userName = ? AND category = 'Chi';";
            cursor = db.rawQuery(sql, new String[]{"%" + description + "%", userName});

            if (cursor.moveToFirst()) {
                do {
                    Expenses obj = new Expenses();
                    obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                    obj.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    obj.setAmount(cursor.getInt(cursor.getColumnIndexOrThrow("amount")));
                    obj.setCategory(cursor.getString(cursor.getColumnIndexOrThrow("category")));
                    obj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                    obj.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("userName")));
                    arr.add(obj);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return arr;
    }



}
