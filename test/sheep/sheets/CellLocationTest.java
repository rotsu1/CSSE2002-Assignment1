package sheep.sheets;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class CellLocationTest {

    @Test
    public void testMaybeReference() {
        CellLocation A = new CellLocation(1, 1);
        Assert.assertEquals(CellLocation.maybeReference("A1"), Optional.of(new CellLocation(1, 'A')));
    }

    @Test
    public void testMaybeReferenceFalse() {
        CellLocation A = new CellLocation(1, 1);
        Assert.assertEquals(CellLocation.maybeReference("3D"), Optional.empty());
    }

    @Test
    public void testMaybeReferenceSpaces() {
        CellLocation A = new CellLocation(1, 1);
        Assert.assertNotEquals(CellLocation.maybeReference("A 1"), Optional.of(new CellLocation(1, 'A')));
    }

    @Test
    public void testGetRow() {
        CellLocation A = new CellLocation(1, 1);
        Assert.assertEquals(A.getRow(), 1);
    }

    @Test
    public void testGetColumn() {
        CellLocation A = new CellLocation(1, 1);
        Assert.assertEquals(A.getColumn(), 1);
    }

    @Test
    public void testEquals() {
        CellLocation A = new CellLocation(1, 1);
        CellLocation B = new CellLocation(1, 1);
        Assert.assertTrue(A.equals(B));
    }

    @Test
    public void testHashCode() {
        CellLocation A = new CellLocation(1, 1);
        CellLocation B = new CellLocation(1, 1);
        Assert.assertEquals(A.hashCode(), B.hashCode());
    }

    @Test
    public void testToString() {
        CellLocation A = new CellLocation(3, 3);
        Assert.assertEquals("C3", A.toString());
    }
}
