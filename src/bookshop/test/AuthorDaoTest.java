package bookshop.test;

import java.util.List;

import bookshop.dao.AuthorDao;
import bookshop.vo.AuthorVo;

public class AuthorDaoTest {

	public static void main(String[] args) {
		
		insertTest("스테파니메이어");
		insertTest("조정래");
		insertTest("김동인");
		insertTest("김난도");
		insertTest("천상병");
		insertTest("원수연");
		
		getListTest();
	}

	public static void getListTest() {

		AuthorDao dao = new AuthorDao();
		List<AuthorVo> list = dao.getList();

		for (AuthorVo vo : list) {
			System.out.println(vo.toString());
		}
	}

	public static void insertTest(String authorName) {

		AuthorVo vo = new AuthorVo();
		vo.setName(authorName);
		
		new AuthorDao().insertAuthor(vo);
	}
}
