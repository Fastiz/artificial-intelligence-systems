package back.algorithms.util;

import back.interfaces.Game;

import java.util.HashMap;

public class Utils {
    public static boolean checkIfHashMapContainsElementAndReplace(HashMap<Game, Integer> hashMap, Game node){
        if(hashMap.containsKey(node)){
            int depth = hashMap.get(node);
            int nodeDepth = node.getDepth();
            if(depth>nodeDepth){
                hashMap.replace(node, nodeDepth);
                return true;
            }else return depth == nodeDepth;
        }
        return true;
    }
}
