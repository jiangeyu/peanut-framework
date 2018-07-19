import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:52 2018/7/13
 * @desc 类扫描器
 */
public interface ClassScanner {

    List<Class<?>> getClassList(String packageName);

    List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

    List<Class<?>> getClassBySuper(String packageName, Class<?> superClass);

}
