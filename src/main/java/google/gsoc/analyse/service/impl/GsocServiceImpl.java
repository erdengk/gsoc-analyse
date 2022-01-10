package google.gsoc.analyse.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import google.gsoc.analyse.mapper.GsocMapper;
import google.gsoc.analyse.model.Gsoc;
import google.gsoc.analyse.service.GsocService;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;


@Service
public class GsocServiceImpl extends ServiceImpl<GsocMapper, Gsoc> implements GsocService {

}
