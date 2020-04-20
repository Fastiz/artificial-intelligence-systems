package src;

import src.models.Alleles;
import src.models.Gen;
import src.pipeline.Mutation;
import src.pipeline.Recombination;
import src.pipeline.Selection;

import java.io.IOException;
import java.util.List;

public class PipelineAdministrator {
    private final Alleles alleles;
    private List<Gen> population;

    private Recombination recombination;
    private Selection selection;
    private Mutation mutation;

    public PipelineAdministrator(int populationSize) throws IOException {
        String folder = "genetic-algorithm/testdata/";
        this.alleles = new Alleles(folder, "cascos.tsv", "pecheras.tsv",
                "armas.tsv", "guantes.tsv", "botas.tsv");

        //TODO: initialize pipeline steps
    }

    public void generateRandomPopulation(){

    }

    public void step(){
        List<Gen> genes = this.population;
        genes = this.recombination.execute(genes, alleles);
        genes = this.mutation.execute(genes, alleles);
        genes = this.selection.execute(genes);
        this.population = genes;
    }
}
