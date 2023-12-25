package adventOfCode.day17;

import java.util.List;

record LocationWithDirection(
    Location location,
    Direction direction,
    int count,
    long heatLoss,
    List<Location> path
){
}
