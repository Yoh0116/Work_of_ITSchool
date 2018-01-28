package method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Updateemp {

	/**
	 * 社員情報を更新します
	 *
	 * @throws IOException
	 *
	 * @param empname 更新する値(社員名)
	 *
	 * @param gender 更新する値(性別)
	 *
	 * @param birthday 更新する値(生年月日)
	 *
	 * @param deptid 更新する値(部署ID)
	 */
	public static void main/*update*/(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		EmpDTO empDTO = new EmpDTO();

		//社員ID入力
		System.out.print("更新する社員の社員IDを入力してください：");
		String str1 = br.readLine();
		int empid = Integer.parseInt(str1);


		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try{
			//DBに接続
			connection = DBManager.getConnection();
			//sql文作成
			String sql = "SELECT * FROM employee WHERE emp_id = ?";
			//ステートメント作成
			preparedStatement = connection.prepareStatement(sql);

			//入力値をバインド
			preparedStatement.setInt(1, empid);

			//sql文実行
			resultSet = preparedStatement.executeQuery();

			empDTO.setEmpid(resultSet.getInt("emp_id"));

			if(empDTO.getEmpid() != 0){
				//社員名入力
				System.out.print("社員名:");
				String empname = br.readLine();

				//性別入力
				System.out.print("性別(1:男性, 2:女性):");
				String str2 = br.readLine();
				int gender = Integer.parseInt(str2);

				//生年月日
				System.out.print("生年月日(西暦年/月/日):");
				String birthday = br.readLine();

				//部署ID
				System.out.print("部署ID(1:営業部, 2:経理部, 3:総務部):");
				String str3 = br.readLine();
				int deptid = Integer.parseInt(str3);

				//sql文作成
				String sql2 = "UPDATE employee SET emp_name = ?, gender = ?, birthday = ?, dept_id = ? WHERE emp_id = ?";
				//ステートメント作成
				preparedStatement = connection.prepareStatement(sql2);

				//入力値をバインド
				preparedStatement.setString(1, empname);
				preparedStatement.setInt(2, gender);
				preparedStatement.setString(3, birthday);
				preparedStatement.setInt(4, deptid);
				preparedStatement.setInt(5, empid);

				//sql文実行
				preparedStatement.executeUpdate();


				System.out.println("社員情報を更新しました");
			}
			System.out.println("該当する社員は登録されていません");


		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.close(preparedStatement);
			DBManager.close(connection);
		}
	}

}
