import java.util.Objects;

public class Pawn {
    public Coordinates position;
    public Team team;
    public int id;

    public Pawn(int x, int y, int _id, Team _team){
        position.x = x;
        position.y = y;
        team = _team;
        id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return id == pawn.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void Move(Action _move){
        //White Team
        if(_move == Action.MOVE && team == Team.WHITE){
            position.y += 1;
        }
        if(_move == Action.CAP_LEFT && team == Team.WHITE){
            position.y += 1;
            position.x -= 1;
        }
        if(_move == Action.CAP_RIGHT && team == Team.WHITE){
            position.y += 1;
            position.x += 1;
        }

        //Black Team
        if(_move == Action.MOVE && team == Team.BLACK){
            position.y -= 1;
        }
        if(_move == Action.CAP_LEFT && team == Team.BLACK){
            position.y -= 1;
            position.x += 1;
        }
        if(_move == Action.CAP_RIGHT && team == Team.BLACK){
            position.y -= 1;
            position.x -= 1;
        }

    }
}
