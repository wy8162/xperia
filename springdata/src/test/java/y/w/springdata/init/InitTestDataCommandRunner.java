package y.w.springdata.init;

import org.springframework.beans.factory.annotation.Autowired;
import y.w.springdata.model.Course;
import y.w.springdata.model.CourseRecord;
import y.w.springdata.model.Rating;
import y.w.springdata.model.Sales;
import y.w.springdata.model.Student;
import y.w.springdata.repository.CourseRecordRepository;
import y.w.springdata.repository.CourseRepository;
import y.w.springdata.repository.SalesRepository;
import y.w.springdata.repository.StudentRepository;

public class InitTestDataCommandRunner //implements CommandLineRunner
{
    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRecordRepository courseRecordRepository;

    //@Override
    public void run(String... args) throws Exception
    {
        salesRepository.save(new Sales( "Marketing", "China", "iPAD", new Double(100.0)));
        salesRepository.save(new Sales( "Marketing", "China", "iPhone", new Double(110.0)));
        salesRepository.save(new Sales( "Sales", "America", "Apple Pencil", new Double(120.0)));
        salesRepository.save(new Sales( "Sales", "America", "Glasses", new Double(130.0)));
        salesRepository.save(new Sales( "Develop", "America", "Bottle", new Double(140.0)));
        salesRepository.save(new Sales( "Develop", "America", "Book", new Double(150.0)));
        salesRepository.save(new Sales( "QA", "America", "iPAD", new Double(160.0)));

        Student andy = Student.builder().id(101L).name("Andy").build();
        Student jack = Student.builder().id(102L).name("Jack").build();
        Student jane = Student.builder().id(103L).name("Jane").build();
        Student mike = Student.builder().id(104L).name("Michael").build();
        Student tom  = Student.builder().id(105L).name("Tom").build();
        Student black= Student.builder().id(106L).name("Black").build();
        Student mark = Student.builder().id(107L).name("Mark").build();

        Course chm01 = Course.builder().id("CHM01").courseName("Chemistry").build();
        Course phy01 = Course.builder().id("PHY01").courseName("Physics").build();
        Course eng01 = Course.builder().id("ENG01").courseName("English").build();
        Course cmp01 = Course.builder().id("CMP01").courseName("Computer").build();
        Course hst01 = Course.builder().id("HST01").courseName("History").build();
        Course mth01 = Course.builder().id("MTH01").courseName("Math").build();
        Course art01 = Course.builder().id("ART01").courseName("Arts").build();

        andy  = studentRepository.save(andy );
        jack  = studentRepository.save(jack );
        jane  = studentRepository.save(jane );
        mike  = studentRepository.save(mike );
        tom   = studentRepository.save(tom  );
        black = studentRepository.save(black);
        mark  = studentRepository.save(mark );

        chm01 = courseRepository.save(chm01);
        phy01 = courseRepository.save(phy01);
        eng01 = courseRepository.save(eng01);
        cmp01 = courseRepository.save(cmp01);
        hst01 = courseRepository.save(hst01);
        mth01 = courseRepository.save(mth01);
        art01 = courseRepository.save(art01);

        CourseRecord cr1 = CourseRecord.builder().student(andy).course(chm01).rating(Rating.NA).build();
        CourseRecord cr2 = CourseRecord.builder().student(andy).course(phy01).rating(Rating.NA).build();
        CourseRecord cr3 = CourseRecord.builder().student(andy).course(art01).rating(Rating.NA).build();
        CourseRecord cr4 = CourseRecord.builder().student(andy).course(hst01).rating(Rating.NA).build();

        cr1 = courseRecordRepository.save(cr1);
        cr2 = courseRecordRepository.save(cr2);
        cr3 = courseRecordRepository.save(cr3);
        cr4 = courseRecordRepository.save(cr4);

        andy.add(cr1).add(cr2).add(cr3).add(cr4);
        andy.add(chm01).add(phy01).add(art01).add(hst01);

        CourseRecord cr5 = CourseRecord.builder().student(jack).course(chm01).rating(Rating.NA).build();
        CourseRecord cr6 = CourseRecord.builder().student(jack).course(eng01).rating(Rating.NA).build();
        CourseRecord cr7 = CourseRecord.builder().student(jack).course(art01).rating(Rating.NA).build();

        cr5 = courseRecordRepository.save(cr5);
        cr6 = courseRecordRepository.save(cr6);
        cr7 = courseRecordRepository.save(cr7);

        jack.add(cr5).add(cr6).add(cr7);
        jack.add(chm01).add(eng01).add(art01);

        CourseRecord cr8 = CourseRecord.builder().student(jane).course(hst01).rating(Rating.NA).build();
        CourseRecord cr9 = CourseRecord.builder().student(jane).course(mth01).rating(Rating.NA).build();
        CourseRecord cr10= CourseRecord.builder().student(jane).course(chm01).rating(Rating.NA).build();

        cr8 = courseRecordRepository.save(cr8);
        cr9 = courseRecordRepository.save(cr9);
        cr10 = courseRecordRepository.save(cr10);

        jane.add(cr8).add(cr9).add(cr10);
        jane.add(chm01).add(mth01).add(hst01);

        chm01.add(cr1).add(cr5).add(cr10);
        chm01.add(andy).add(jack).add(jane);

        phy01.add(cr2);
        phy01.add(andy);

        art01.add(cr3).add(cr7);
        art01.add(andy).add(jack);

        hst01.add(cr4).add(cr8);
        hst01.add(andy).add(jane);

        eng01.add(cr6);
        eng01.add(jack);

        mth01.add(cr9);
        mth01.add(jane);

        studentRepository.save(andy);
        studentRepository.save(jack);
        studentRepository.save(jane);

        courseRepository.save(chm01);
        courseRepository.save(phy01);
        courseRepository.save(eng01);
        courseRepository.save(hst01);
        courseRepository.save(mth01);
        courseRepository.save(art01);
    }
}
