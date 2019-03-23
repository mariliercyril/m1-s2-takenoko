package com.cco.takenoko.player;

import java.awt.Point;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cco.takenoko.game.Game;
import com.cco.takenoko.game.objective.Objective;
import com.cco.takenoko.game.objective.ObjectivePool;
import com.cco.takenoko.game.objective.ObjectiveType;
import com.cco.takenoko.game.tiles.Tile;
import com.cco.takenoko.tool.ForbiddenActionException;

@RestController
public class ClientBot extends Player {

	@RequestMapping("/")
	public String home() {

		return "Hello world!";
	}

	@Override
	protected ObjectiveType whatTypeToDraw(ObjectivePool pool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean keepIrrigation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean putDownIrrigation(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Action[] planActions(Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Point whereToPutDownTile(Game game, Tile t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Tile chooseTile(Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Point whereToMoveGardener(Game game, List<Point> available) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Point whereToMovePanda(Game game, List<Point> available) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Objective chooseObjectiveToValidate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void tileImprovement(Game game, List<Tile> improvableTiles) throws ForbiddenActionException {
		// TODO Auto-generated method stub
		
	}

}
