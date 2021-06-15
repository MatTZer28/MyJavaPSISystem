package product.sell.stage.ranking;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Controller implements Initializable {
	
	@FXML
    private AnchorPane anchorRoot;
	
	@FXML
    private Button Button_returnButton;
	
	@FXML
    private CategoryAxis xAixs;

    @FXML
    private NumberAxis yAixs;
    
    @FXML
    private BarChart<String, Integer> BarChart_barChart;
	
	@FXML
    private TableView<RankDataForRankTable> TableView_rankTable;

    @FXML
    private TableColumn<RankDataForRankTable, String> TableColumn_rank;
    @FXML
    private TableColumn<RankDataForRankTable, String> TableColumn_combineName;
    @FXML
    private TableColumn<RankDataForRankTable, String> TableColumn_totalSells;
    
    private ObservableList<RankDataForRankTable> rankTableItems;
    
    private ResultSet resultSetForRankTable;
	
	@FXML
    private ToolBar ToolBar_toolBar;
	
	private double xOffset;
	private double yOffset;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		setStageDragable();
		setRankTableColumn();
		setSellTableItems();
		setUpBarChart();
	}
	
	public void setStageDragable() {
		ToolBar_toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = main.Main.getSellStage().getX() - event.getScreenX();
				yOffset = main.Main.getSellStage().getY() - event.getScreenY();
			}
		});

		ToolBar_toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				main.Main.getSellStage().setX(event.getScreenX() + xOffset);
				main.Main.getSellStage().setY(event.getScreenY() + yOffset);
			}
		});
	}
	
	public void setRankTableColumn() {
		TableColumn_rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		TableColumn_combineName.setCellValueFactory(new PropertyValueFactory<>("combineName"));
		TableColumn_totalSells.setCellValueFactory(new PropertyValueFactory<>("totalSell"));
	}
	
	public void setSellTableItems() {
		rankTableItems = getRankData();
		TableView_rankTable.setItems(rankTableItems);
	}

	public ObservableList<RankDataForRankTable> getRankData() {
		retriveDataFromDBForRankTable();
		ObservableList<RankDataForRankTable> ranks = FXCollections.observableArrayList();
		int count = 0;
		try {
			while (resultSetForRankTable.next()) {
				count++;
				String combineName = resultSetForRankTable.getString(1);
				String totalAmount = resultSetForRankTable.getString(2);
				ranks.add(new RankDataForRankTable(String.valueOf(count), combineName, totalAmount));
				setUpBarChartItems(combineName, Integer.parseInt(totalAmount));
			}
		} catch (SQLException e) {
			return FXCollections.observableArrayList();
		}
		return ranks;
	}

	public void retriveDataFromDBForRankTable() {
		try {
			Statement statement = main.Main.getConnection().createStatement();
			resultSetForRankTable = statement.executeQuery("SELECT DISTINCT CombineName, SUM(Amount)Amount FROM sellcombine GROUP BY CombineName ORDER BY Amount DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public class RankDataForRankTable {
		private String rank;
		private String combineName;
		private String totalSell;
		
		public RankDataForRankTable(String rank, String combineName, String totalSell) {
			this.rank = rank;
			this.combineName = combineName;
			this.totalSell = totalSell;
		}

		public String getRank() {
			return rank;
		}

		public void setRank(String rank) {
			this.rank = rank;
		}

		public String getCombineName() {
			return combineName;
		}

		public void setCombineName(String combineName) {
			this.combineName = combineName;
		}

		public String getTotalSell() {
			return totalSell;
		}

		public void setTotalSell(String totalSell) {
			this.totalSell = totalSell;
		}
	}
	
	public void setUpBarChart() {
		xAixs.setLabel("商品組合名稱");
		yAixs.setLabel("銷售總量");
	}
	
	public void setUpBarChartItems(String combineName, int totalAmount) {
		XYChart.Series<String, Integer> dataSeries = new Series<String, Integer>();
		dataSeries.setName(combineName);
		dataSeries.getData().add(new XYChart.Data<String, Integer>("", totalAmount));
		BarChart_barChart.getData().add(dataSeries);
	}

    @FXML
    void returnButtonClicked(ActionEvent event) throws IOException {
    	Parent root =(Parent ) FXMLLoader.load(getClass().getResource("/fxml/SellUI.fxml"));
		Scene scene = Button_returnButton.getScene();
		
		root.translateXProperty().set(-scene.getWidth());
		StackPane parentContainer = (StackPane ) scene.getRoot();
		parentContainer.getChildren().add(root);
		
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.setOnFinished(e -> {
			parentContainer.getChildren().remove(anchorRoot);
		});
		timeline.play();
    }
}