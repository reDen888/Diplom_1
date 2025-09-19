package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

// Отдельный класс для непараметризованных тестов Burger
public class BurgerNonParameterizedTest {

    protected Burger burger;

    @Mock
    protected Bun bun;

    @Mock
    protected Ingredient cutlet;

    @Mock
    protected Ingredient cheese;

    @Mock
    protected Ingredient mayonnaise;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Настраиваем моки
        when(bun.getName()).thenReturn("Чёрная булка");
        when(bun.getPrice()).thenReturn(100f);

        when(cutlet.getName()).thenReturn("Котлета");
        when(cutlet.getType()).thenReturn(IngredientType.FILLING);
        when(cutlet.getPrice()).thenReturn(50f);

        when(cheese.getName()).thenReturn("Сыр");
        when(cheese.getType()).thenReturn(IngredientType.FILLING);
        when(cheese.getPrice()).thenReturn(50f);

        when(mayonnaise.getName()).thenReturn("Майонез");
        when(mayonnaise.getType()).thenReturn(IngredientType.SAUCE);
        when(mayonnaise.getPrice()).thenReturn(50f);

        burger = new Burger();
    }

    @Test
    public void testReceiptFormatWithOneIngredient() {
        burger.setBuns(bun);
        burger.addIngredient(cutlet);

        String receipt = burger.getReceipt();

        // Проверяем наличие ключевых элементов
        assertTrue("Чек должен содержать название булки", receipt.contains("Чёрная булка"));
        assertTrue("Чек должен содержать ингредиент", receipt.contains("Котлета"));
        assertTrue("Чек должен содержать цену", receipt.contains("Price:"));
        // Проверяем что цена присутствует в чеке
        assertTrue("Чек должен содержать цену", receipt.contains("250"));
    }

    @Test
    public void testReceiptFormatGeneral() {
        burger.setBuns(bun);
        burger.addIngredient(cutlet);

        String receipt = burger.getReceipt();

        // Общая проверка формата без конкретных значений
        assertTrue("Чек должен содержать название булки", receipt.contains(bun.getName()));
        assertTrue("Чек должен содержать ингредиент", receipt.contains(cutlet.getName()));
        assertTrue("Чек должен содержать цену", receipt.contains("Price:"));
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bun);
        assertNotNull("Булочка должна быть установлена", burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(cutlet);
        assertEquals("Должен быть добавлен один ингредиент", 1, burger.ingredients.size());
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(cutlet);
        burger.removeIngredient(0);
        assertEquals("Список ингредиентов должен быть пустым после удаления", 0, burger.ingredients.size());
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(cutlet);
        burger.addIngredient(cheese);
        burger.addIngredient(mayonnaise);

        burger.moveIngredient(0, 1);

        assertEquals("Ингредиент cutlet должен быть перемещен на позицию 1", cutlet, burger.ingredients.get(1));
        assertEquals("Ингредиент cheese должен переместиться на позицию 0", cheese, burger.ingredients.get(0));
        assertEquals("Ингредиент mayonnaise должен остаться на позиции 2", mayonnaise, burger.ingredients.get(2));
    }

    @Test
    public void testBurgerWithBunOnly() {
        burger.setBuns(bun);
        assertEquals("Цена бургера только с булкой должна быть 2 * price булки", 200f, burger.getPrice(), 0.01);
    }

    @Test(expected = NullPointerException.class)
    public void testGetPriceWithoutBun() {
        burger.getPrice();
    }

    @Test(expected = NullPointerException.class)
    public void testGetReceiptWithoutBun() {
        burger.getReceipt();
    }

    @Test
    public void testEmptyBurgerReceipt() {
        burger.setBuns(bun);
        String receipt = burger.getReceipt();

        assertTrue("Чек должен содержать название булки", receipt.contains("Чёрная булка"));
        assertTrue("Чек должен содержать цену", receipt.contains("Price:"));
        // Проверяем что цена присутствует в чеке
        assertTrue("Чек должен содержать цену", receipt.contains("200"));
    }

    @Test
    public void testReceiptContainsCorrectPrice() {
        burger.setBuns(bun);
        burger.addIngredient(cutlet);

        String receipt = burger.getReceipt();
        float expectedPrice = 250f;

        // Проверяем что цена присутствует в чеке
        assertTrue("Чек должен содержать правильную цену",
                receipt.contains(String.valueOf((int)expectedPrice)) ||
                        receipt.contains(String.format("%.1f", expectedPrice)) ||
                        receipt.contains(String.format("%.0f", expectedPrice)));
    }
}