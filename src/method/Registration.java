package method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Registration {

	/**
	 * 社員情報を登録します
	 *
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//社員名入力
		System.out.print("社員名:");
		String empname = br.readLine();

		//性別入力
		System.out.print("性別(1:男性, 2:女性):");
		String str1 = br.readLine();
		int gender = Integer.parseInt(str1);

		//生年月日
		System.out.print("生年月日(西暦年/月/日):");
		String birthday = br.readLine();

		//部署ID
		System.out.print("部署ID(1:営業部, 2:経理部, 3:総務部):");
		String str2 = br.readLine();
		int deptid = Integer.parseInt(str2);

		//DBに登録
		insert(empname, gender, birthday, deptid);

		System.out.println("社員情報を登録しました");

	}



	public static void insert(String name,int gender, String birthday, int deptid){

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try{
			//DBに接続
			connection = DBManager.getConnection();
			//sql文作成
			String sql = "INSERT INTO employee VALUES(?,?,?,?)";
			//ステートメント作成
			preparedStatement = connection.prepareStatement(sql);

			//入力値をバインド
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, gender);
			preparedStatement.setString(3, birthday);
			preparedStatement.setInt(4, deptid);

			//sql文実行
			preparedStatement.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}

	}

}
