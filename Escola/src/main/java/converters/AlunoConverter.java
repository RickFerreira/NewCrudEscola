package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import entities.Aluno;
@FacesConverter(value = "alunoPickListConverter")
public class AlunoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object ret = null;
		if (component instanceof PickList) {
			Object dualList = ((PickList) component).getValue();
			DualListModel dl = (DualListModel) dualList;
			for (Object o : dl.getSource()) {
				String id = "" + ((Aluno) o).getId();
				if (value.equals(id)) {
					ret = o;
					break;
				}
			}
			if (ret == null)
				for (Object o : dl.getTarget()) {
					String id = "" + ((Aluno) o).getId();
					if (value.equals(id)) {
						ret = o;
						break;
					}
				}
		}
		return ret;

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String str = "";
		if (value instanceof Aluno) {
			str = "" + ((Aluno) value).getId();
		}
		return str;
	}

}
