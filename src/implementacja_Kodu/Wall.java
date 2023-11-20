package implementacja_Kodu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

public class Wall implements Structure {
    private List<Block> blocks;

    // poprzez cykl for sprawdza kazdy element w liscie
    @Override
    public Optional<Block> findBlockByColor(String color) {
        for (Block block : blocks){
            if (block.getColor().equals(color)){
                return Optional.of(block);
            }

            // jesli w liscie znajduje sie CompositeBlock, ma zrobic iteracje dla kazdego elementu
            if(block instanceof CompositeBlock compositeBlock){
                this.blocks = compositeBlock.getBlocks();

                Optional<Block> result = findBlockByColor(color);
                if (result.isPresent()){
                    return result;
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> result = new ArrayList<>();

        for (Block block : blocks){
            if(block.getMaterial().equals(material)){
                result.add(block);
            }

            if(block instanceof CompositeBlock compositeBlock){
                List<Block> blockList = compositeBlock.getBlocks();

                for (Block compBlock : blockList){
                    if(compBlock.getMaterial().equals(material)){
                        result.add(compBlock);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public int count() {
        int resultCount = 0;

        for (Block block : blocks) {
            if(block != null){
                if(block instanceof CompositeBlock compositeBlock){
                    List<Block> blockList = compositeBlock.getBlocks();

                    for (Block blockIn : blockList) resultCount++;
                }else {
                    resultCount++;
                }
            }
        }

        return resultCount;
    }
}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}