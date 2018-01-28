package method;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteEmp {

	/**
	 * 社員情報を全て削除します
	 *
	 * @throws IOException
	 *
	 * @param empid 削除する社員情報のID(社員ID)
	 *
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

				//sql文作成
				String sql2 = "DELETE FROM employee WHERE emp_id = ?";
				//ステートメント作成
				preparedStatement = connection.prepareStatement(sql2);

				//入力値をバインド
				preparedStatement.setInt(1, empid);

				//sql文実行
				preparedStatement.executeUpdate();


				System.out.println("社員情報を削除しました");
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
