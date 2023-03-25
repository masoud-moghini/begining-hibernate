package validator;

import ch07.validated.Coordinate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.util.JPASessionUtil;

import javax.validation.ConstraintViolationException;

public class CoordinateTest {
    private void persist(Coordinate entity) {
        try (Session session = JPASessionUtil.getSession("chapter07")) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }
    @DataProvider(name = "validCoordinates")
    private Object[][] validCoordinates() {
        return new Object[][]{
                {1, 1},
                {-1, 1},
                {1, -1},
                {1, 0},
                {-1, 0},
                {0, -1},
                {0, 1},
                {0, 0},
        };
    }

    @Test(dataProvider = "validCoordinates")
    public void testValidCoordinate(Integer x, Integer y) {
        Coordinate c = Coordinate.builder().x(x).y(y).build();
        persist(c);
        // has passed validation, if we reach this point.
    }
    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testInvalidCoordinate() {
        testValidCoordinate(-1, -1);
    }

}
