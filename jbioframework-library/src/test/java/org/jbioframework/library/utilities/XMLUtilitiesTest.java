package org.jbioframework.library.utilities;

import org.jbioframework.library.protein.Protein;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class XMLUtilitiesTest {
    @Test
    void xmlFile() {
        ArrayList<Protein> proteins = new ArrayList<>();
        proteins.add(new Protein("VDKWQFPITKTGSFGMSYSVGDRTVLNIILERPMEWWMWWYQSKSGANST"));
        proteins.add(new Protein("APGTFLWGSLCDQNNAPSHATHICHDPDWRIQDQQQIMSLHWNPKLDDCE"));
        proteins.add(new Protein("MPLLVKMFNDWCTWGPAIVKYNRLGSPSQFNYTGYDHRTKTLDTKHLHSN"));
        proteins.add(new Protein("GICQLECRPGACHLYWGGAWSRSMWAMQMHSIECYGTTNMFAYEIITAKH"));
        proteins.add(new Protein("PVTRSINNCKYDCYSQTWHPIWWEVMGGTFTFHFPWTVCHAEDQPDNIKR"));
        XMLUtilities.saveFile("testFile",proteins);
        ArrayList<Protein> loadedProteins = XMLUtilities.loadFile("testFile");
        assertEquals(proteins,loadedProteins);
    }


}