package sheep.expression.basic;

import org.junit.Assert;
import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.TypeError;

import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

/**
 * Test class for reference.
 */
public class ReferenceTest {

    @Test
    public void testToString() {
        Reference A = new Reference("A");
        Assert.assertEquals("REFERENCE(A)", A.toString());
    }

    @Test
    public void testToStringWithInt() {
        Reference B = new Reference("B");
        Assert.assertEquals("REFERENCE(B)", B.toString());
    }

    @Test
    public void testEqualsFalse() {
        Reference C = new Reference("C");
        Reference D = new Reference("D");
        Assert.assertFalse(D.equals(C));
    }

    @Test
    public void testEqualsTrue() {
        Reference E = new Reference("E");
        Reference F = E;
        Assert.assertTrue(F.equals(E));
    }

    @Test
    public void testHashCode() {
        Reference G = new Reference("G");
        Reference GG = new Reference("G");
        Assert.assertEquals(G.hashCode(), GG.hashCode());
    }

    @Test
    public void testDependenciesTrue() {
        Reference H = new Reference("H");
        Reference I = H;
        Assert.assertEquals(I.dependencies(), H.dependencies());
    }

    @Test
    public void testDependenciesFalse() {
        Reference J = new Reference("J");
        Reference K = new Reference("K");
        Assert.assertNotEquals(J.dependencies(), K.dependencies());
    }

    @Test
    public void testValue() throws TypeError {
        Map<String, Expression> state = new HashMap<>();
        Reference L = new Reference("L");
        Assert.assertEquals(L, L.value(state));
    }

    @Test
    public void testRender() {
        Reference hello = new Reference("hello");
        Assert.assertEquals(hello.render(), "hello");
    }
}
