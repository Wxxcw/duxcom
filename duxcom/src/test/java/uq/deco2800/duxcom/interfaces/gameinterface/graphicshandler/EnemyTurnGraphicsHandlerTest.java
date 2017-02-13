package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.testfx.framework.junit.ApplicationTest;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.particles.IconType;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.setupStage;

/**
 * Test EnemyTurnGraphicsHandler
 *
 * Created by jay-grant on 21/09/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnemyTurnGraphicsHandlerTest extends ApplicationTest {

    private EnemyTurnGraphicsHandler enemyTurnGraphicsHandler;
    private GraphicsContext graphicsContext;

    @Mock
    private GameManager gameManagerMock;
    @Mock
    private MapAssembly mapAssemblyMock;
    @Mock
    private AbstractHero abstractHeroMock;
    @Mock
    private AbstractEnemy abstractEnemyMock;
    @Mock
    private AbstractAbility abstractAbilityMock;

    @Test
    public void testUpdateGraphics() {
        when(gameManagerMock.getMap()).thenReturn(mapAssemblyMock);
        when(gameManagerMock.getScale()).thenReturn(0.5);
        when(gameManagerMock.getxOffset()).thenReturn(10.0);
        when(gameManagerMock.getyOffset()).thenReturn(10.0);
        when(mapAssemblyMock.getWidth()).thenReturn(20);
        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        when(abstractEnemyMock.getX()).thenReturn(1);
        when(abstractEnemyMock.getY()).thenReturn(1);
        when(gameManagerMock.getCurrentAbilityCast()).thenReturn(abstractAbilityMock);
        when(abstractAbilityMock.getCastIcon()).thenReturn(IconType.SPLAT_DEFAULT);
        when(abstractAbilityMock.getHitIcon()).thenReturn(IconType.SPLAT_DEFAULT);
        when(abstractAbilityMock.getSplatIcon()).thenReturn(IconType.SPLAT_DEFAULT);

        when(gameManagerMock.getCurrentAbilityOwnerX()).thenReturn(1);
        enemyTurnGraphicsHandler.updateGraphics(graphicsContext);
        verify(abstractAbilityMock, times(1)).getCastIcon();

        when(gameManagerMock.getCurrentAbilityOwnerX()).thenReturn(-1);
        when(gameManagerMock.getCurrentAbilityTargetX()).thenReturn(1);
        when(gameManagerMock.getHitOrSplat()).thenReturn(true);
        enemyTurnGraphicsHandler.updateGraphics(graphicsContext);
        verify(abstractAbilityMock, times(1)).getHitIcon();

        when(gameManagerMock.getHitOrSplat()).thenReturn(false);
        enemyTurnGraphicsHandler.updateGraphics(graphicsContext);
        verify(abstractAbilityMock, times(2)).getSplatIcon();
    }

    @Test
    public void testNeedsUpdating() {
        when(gameManagerMock.isTurnGraphicsChanged()).thenReturn(false);
        assertFalse(enemyTurnGraphicsHandler.needsUpdating());

        when(gameManagerMock.isTurnGraphicsChanged()).thenReturn(true);
        assertTrue(enemyTurnGraphicsHandler.needsUpdating());
    }

    @Test
    public void testAddImage() {
        when(gameManagerMock.getxOffset()).thenReturn(10.0);
        when(gameManagerMock.getyOffset()).thenReturn(10.0);
        enemyTurnGraphicsHandler.addImage("real_qut");
        verify(gameManagerMock, times(1)).getxOffset();
        verify(gameManagerMock, times(1)).getyOffset();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(canvas);
        Scene scene = new Scene(anchorPane, 1280, 720);
        stage.setScene(scene);
        stage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        new GameLoop(10, new AtomicBoolean(false), gameManagerMock);
        enemyTurnGraphicsHandler = new EnemyTurnGraphicsHandler();
    }

    @AfterClass
    public static void cleanUp() throws TimeoutException {
        setupStage(Stage::close);
        new GameLoop(0, null, null);
    }
}
