package google.gsoc.analyse.model;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Gsoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer year;

    private String name;

    private String gsocUrl;

    private String logImg;

    private String topics;

    private String technologies;

    private String introduction;

    private String organizationUrl;

    private String fullIntroduction;

    private String guide;

    private String ideas;


}
