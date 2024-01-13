package pap.db.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pap.db.Entities.Branch;

import static org.junit.jupiter.api.Assertions.*;

class BranchRepositoryTest {
    BranchRepository branchRepository = new BranchRepository();

    @Test
    void getAll() {
        Branch branch = branchRepository.getById(1);
        Assertions.assertNotNull(branch);
    }

    @Test
    void getById() {
        Branch branch = branchRepository.getById(1);
        Assertions.assertNotNull(branch);
    }
}