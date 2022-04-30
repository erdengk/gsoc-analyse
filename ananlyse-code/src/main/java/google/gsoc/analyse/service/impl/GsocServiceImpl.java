package google.gsoc.analyse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import google.gsoc.analyse.mapper.GsocMapper;
import google.gsoc.analyse.model.Gsoc;
import google.gsoc.analyse.service.GsocService;
import org.springframework.stereotype.Service;


@Service
public class GsocServiceImpl extends ServiceImpl<GsocMapper, Gsoc> implements GsocService {

}
