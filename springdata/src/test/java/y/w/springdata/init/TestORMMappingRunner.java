package y.w.springdata.init;

import org.springframework.beans.factory.annotation.Autowired;
import y.w.springdata.model.more.M121c;
import y.w.springdata.model.more.M121p;
import y.w.springdata.model.more.M12Mc;
import y.w.springdata.model.more.M12Mp;
import y.w.springdata.model.more.MM2Mc;
import y.w.springdata.model.more.MM2Mp;
import y.w.springdata.repository.more.M121cRepository;
import y.w.springdata.repository.more.M121pRepository;
import y.w.springdata.repository.more.M12McRepository;
import y.w.springdata.repository.more.M12MpRepository;
import y.w.springdata.repository.more.MM2McRepository;
import y.w.springdata.repository.more.MM2MpRepository;

public class TestORMMappingRunner //implements CommandLineRunner
{
    @Autowired
    private M121cRepository m121cRepository;

    @Autowired
    private M121pRepository m121pRepository;

    @Autowired
    private M12MpRepository m12MpRepository;

    @Autowired
    private M12McRepository m12McRepository;

    @Autowired
    private MM2MpRepository mm2MpRepository;

    @Autowired
    private MM2McRepository mm2McRepository;

    public TestORMMappingRunner()
    {
    }

    //@Override
    public void run(String... args) throws Exception
    {
        // OneToOne
        M121p m121p = M121p.builder().pinfo("some Parent Info").build();
        M121c m121c = M121c.builder().cinfo("some Chind info").build();
        m121p.setM121c(m121c);

        // This save will cascade to m121c as well based on OneToOne relationship
        m121pRepository.save(m121p);

        // OneToTwo
        M12Mp m12mp = M12Mp.builder().pinfo("some Parent Info").build();
        M12Mc m12mc = M12Mc.builder().cinfo("some Chind info").build();
        m12mp.addM12Mc(m12mc);
        m12mc = M12Mc.builder().cinfo("some Chind info2").build();
        m12mp.addM12Mc(m12mc);

        // This save will cascade to m12mc as well based on OneToOne relationship
        m12MpRepository.save(m12mp);

        m12mp = M12Mp.builder().pinfo("some Parent Info3").build();
        m12mc = M12Mc.builder().cinfo("some Chind info3").build();
        m12mp.addM12Mc(m12mc);

        // This save will cascade to m12mc as well based on OneToOne relationship
        m12McRepository.save(m12mc);

        // ManyToMany
        // mm2Mp has two MM2Mc
        MM2Mp mm2Mp = MM2Mp.builder().pinfo("MM2Mp info primary").build();
        MM2Mc mm2Mc = MM2Mc.builder().cinfo("MM2Mc info1").build();
        mm2Mp.addMM2Mc(mm2Mc);
              mm2Mc = MM2Mc.builder().cinfo("MM2Mc info2").build();
        mm2Mp.addMM2Mc(mm2Mc);

        mm2MpRepository.save(mm2Mp);

        // mm2Mc has 1 MM2Mp
        mm2Mc = MM2Mc.builder().cinfo("MM2Mc info primary").build();
        mm2Mp = MM2Mp.builder().pinfo("MM2Mp info1").build();
        mm2Mc.addMM2Mp(mm2Mp);

        mm2McRepository.save(mm2Mc);
    }
}
